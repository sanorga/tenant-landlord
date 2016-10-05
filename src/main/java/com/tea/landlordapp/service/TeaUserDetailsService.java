package com.tea.landlordapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tea.landlordapp.dto.TeaAuthority;
import com.tea.landlordapp.dto.TeaUserDetails;

public class TeaUserDetailsService implements UserDetailsService {

	   protected final Logger logger = LoggerFactory.getLogger(getClass());
	   
	private JdbcTemplate jdbcTemplate;
	
	public TeaUserDetailsService(DataSource dataSource) {
		super();
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {

		Detail detail = getUserDetail(arg0);
		
		Collection<TeaAuthority> authorities = getAuthoritiesByDetail(detail);
		
		boolean notLocked = !StringUtils.equals(detail.status, "L") || StringUtils.equals(detail.status, "X");
		if (detail.lockoutUntil != null){
			notLocked = notLocked && ((new Date()).compareTo(detail.lockoutUntil) > 0);
		}
		
		TeaUserDetails newDetail = new TeaUserDetails(authorities, 
							detail.password, 
							detail.username,
							!StringUtils.equals(detail.status, "I"),
							notLocked,
							true,
							(StringUtils.equals(detail.status, "A") || StringUtils.equals(detail.status, "X")) && StringUtils.equals(detail.subscriberStatus, "A"),
							detail.userId, detail.mfaDesired, detail.mfaRequired, 
							detail.secondFactorValid, detail.role,
							StringUtils.equalsIgnoreCase(detail.subscriberType, "P"),
							StringUtils.equalsIgnoreCase(detail.subscriberType, "C"),
							StringUtils.equalsIgnoreCase(detail.subscriberType, "S"),
							detail.questions == 3);
		
		return newDetail;
	}
	
	private Detail getUserDetail(String userName){
		StringBuilder sb = new StringBuilder();
		sb.append("select u.id, u.email_id, u.password, u.status,");
		sb.append(" u.enable_mfa mfa_desired, coalesce(pp.require_mfa,0) mfa_required,");
		sb.append(" coalesce(pp.max_age,0) max_age, coalesce(datediff(now(),ph.date_changed),-1) password_age,");
		sb.append(" 'Y' tenant_screen, 'N' emp_screen, r.role, 'S' subscriber_type, u.lockout_until, 'A' subscriber_status");
		sb.append(" , if(u.question1 is null,0,1) + if(u.question2 is null,0,1) + if(u.question3 is null,0,1) questions");
		sb.append(" from user u");
		sb.append(" inner join role r on r.id = u.role_id");
//		sb.append(" inner join subscriber s on s.id = u.subscriber_id");
		sb.append(" left join password_policy pp on pp.id = r.password_policy_id");
		sb.append(" left join (");
		sb.append("   select user_id, date_changed");
		sb.append("   from password_history");
		sb.append("   order by date_changed desc limit 1");
		sb.append(" ) ph on ph.user_id = u.id");
		sb.append(" where u.email_id = ?");
		
		Detail detail;
		try {
			detail = this.jdbcTemplate.queryForObject(sb.toString(),
					new Object[]{userName},
					new RowMapper<Detail>(){
							public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
								Detail det = new Detail();
								det.userId = rs.getInt("id");
								det.username = rs.getString("email_id");
								det.password = rs.getString("password");
								det.status = rs.getString("status");
								det.mfaDesired = rs.getBoolean("mfa_desired");
								det.mfaRequired = rs.getBoolean("mfa_required");
								det.maxAge = rs.getInt("max_age");
								det.passwordAge = rs.getInt("password_age");
								det.allowEmpScreen = StringUtils.equalsIgnoreCase("Y", rs.getString("emp_screen"));
								det.allowTenantScreen = StringUtils.equalsIgnoreCase("Y", rs.getString("tenant_screen"));
								det.role = rs.getString("role");
								det.subscriberType = rs.getString("subscriber_type");
								det.lockoutUntil = rs.getTimestamp("lockout_until");
								det.subscriberStatus = rs.getString("subscriber_status");
								det.questions = rs.getInt("questions");
								return det;
							}
						});
		} catch (DataAccessException e1) {
			throw new UsernameNotFoundException("Invalid Username");
		}
		
		if (detail == null) throw new UsernameNotFoundException("Invalid Username");

		return detail;
	}
	
	private Collection<TeaAuthority> getAuthoritiesByDetail(Detail detail){
		boolean expired = false;
		if (detail.maxAge > 0){
			if (detail.passwordAge < 0){
				expired = true;
			} else {
				expired = (detail.maxAge < detail.passwordAge);
			}
		}
		
		Collection<TeaAuthority> authorities = new ArrayList<TeaAuthority>();
		boolean doMFA = detail.mfaDesired || detail.mfaRequired;
		if (StringUtils.equalsIgnoreCase(detail.status, "X") || expired){
			authorities.add(new TeaAuthority("must.change.password"));
		} else if (doMFA && !detail.secondFactorValid) {
			authorities.add(new TeaAuthority("dual.authentication.required"));
			authorities.add(new TeaAuthority("USER"));
//		} else if ((!detail.mfaDesired && !detail.mfaRequired) && !detail.secondFactorValid) {
//			authorities.add(new TeaAuthority("USER"));
//			if (detail.questions == 3){
//				authorities.add(new TeaAuthority("security.question.required"));
//			} else {
//				authorities.add(new TeaAuthority("must.setup.questions"));
//			}
		} else {		
		
			List<String> caps = this.jdbcTemplate.queryForList("select distinct c.request_id from role r inner join user u on u.role_id = r.id inner join role2capability r2c on r2c.role_id = r.id inner join capability c on c.id = r2c.capability_id where u.email_id = ?", String.class, new Object[]{detail.username});
			for (String cap : caps) {
				authorities.add(new TeaAuthority(cap));
			}
			if (detail.allowEmpScreen) authorities.add(new TeaAuthority("new.employment.application"));
			if (detail.allowTenantScreen) authorities.add(new TeaAuthority("new.tenant.application"));
		}
		return authorities;
	}
	
	private Collection<TeaAuthority> getUserAuthorities(String userName, Boolean mfaValid){
		Detail detail = getUserDetail(userName);
		if (mfaValid != null){
			detail.secondFactorValid = mfaValid;
		}
		
		Collection<TeaAuthority> authorities = getAuthoritiesByDetail(detail);

		return authorities;
	}
	
	public TeaUserDetails updateUserDetailAuthorities(TeaUserDetails userDetails, Boolean mfaValid){
		
		Collection<TeaAuthority> authorities = getUserAuthorities(userDetails.getUsername(), mfaValid);
		boolean sfv = userDetails.isSecondFactorValid();
		if (mfaValid != null){
			sfv = mfaValid;
		}
		
		TeaUserDetails newDetail = new TeaUserDetails(authorities, 
				userDetails.getPassword(), 
				userDetails.getUsername(),
				userDetails.isAccountNonExpired(),
				userDetails.isAccountNonLocked(),
				true,
				userDetails.isEnabled(),
				userDetails.getUserId(), userDetails.isMfaDesired(), userDetails.isMfaRequired(), 
				sfv, 
				userDetails.getPrimaryRole(),
				userDetails.isPartnerUser(),
				userDetails.isClientUser(),
				userDetails.isSystemAdminUser(),
				userDetails.isQuestions());

		return newDetail;
	}
	
	private class Detail {
		private String username;
		private String password;
		private String status;
		private Integer userId;
		private Boolean mfaDesired;
		private Boolean mfaRequired;
		private Boolean secondFactorValid = false;
		private Integer maxAge;
		private Integer passwordAge;
		private Boolean allowTenantScreen = false;
		private Boolean allowEmpScreen = false;
		private String role;
		private String subscriberType;
		private Date lockoutUntil;
		private String subscriberStatus;
		private Integer questions;
	}

}
