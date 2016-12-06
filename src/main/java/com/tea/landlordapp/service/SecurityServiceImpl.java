package com.tea.landlordapp.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.PasswordHistory;
import com.tea.landlordapp.domain.PasswordPolicy;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaAuthority;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.UserDao;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7744627632217376257L;
	
	@Autowired
	SimpleDao simpleDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	StandardPasswordEncoder standardPasswordEncoder;
	
	@Autowired
	TeaUserDetailsService teaUserDetailsService;

	@Override
	public void updatePassword(User user, String newPassword) {
		String oldHash = user.getPasswordHash();
		PasswordHistory pwh = new PasswordHistory();

		pwh.setUser(user);
		pwh.setDateChanged(new Date());
		pwh.setPassword(oldHash);

		simpleDao.persist(pwh);

		user.setPassword(newPassword);
		user.setStatus('A');
		user = simpleDao.merge(user);

	}
	
	@Override
	public void updateYourPassword(User user, String newPassword, String passwordHash) {
		String oldHash = passwordHash;
		PasswordHistory pwh = new PasswordHistory();

		pwh.setUser(user);
		pwh.setDateChanged(new Date());
		pwh.setPassword(oldHash);

		simpleDao.persist(pwh);

		user.setPassword(newPassword);
		user.setStatus('A');
		user = simpleDao.merge(user);

	}
	
	@Override
	public boolean passwordIsPolicyCompliant(String password, PasswordPolicy policy){
	       int len = password.length();
	       int digit = 0;
	       int lowerCase = 0;
	       int upperCase = 0;
	       int special = 0;
	       int count = 0;
	       int matches = 0;

	       char ch;


	         if(len >= policy.getMinLength() && len <= policy.getMaxLength())
	         {
	           while(count < len)
	           {
	             ch = password.charAt(count);
	             if(Character.isDigit(ch))
	             {
	               digit = digit + 1;
	             } else if(Character.isLowerCase(ch))
		             {
		               lowerCase = lowerCase + 1;
		             } else if(Character.isUpperCase(ch))
			             {
			               upperCase = upperCase + 1;
			             } else if(Character.isUpperCase(ch))
			             	{
				               special = special + 1;
				             }
	               count = count + 1;
	           }
	         }
	         
	         if (digit >= policy.getMinDigit()) matches++;
	         if (lowerCase >= policy.getMinLowercase()) matches++;
	         if (upperCase >= policy.getMinUppercase()) matches++;
	         if (special >= policy.getMinSpecialCharacter()) matches++;
	         
	         return (matches >= policy.getMinMatches());
	}
	
	@Override
	public boolean passwordInRecentHistory(Integer userId, String password, PasswordPolicy policy){
		boolean res = false;
//		PasswordPolicy policy = user.getRole().getPasswordPolicy();
		if (policy != null && policy.getMinRememberedPassword() > 0){
			List<String> history = userDao.getPasswordHistory(userId, policy.getMinRememberedPassword());
			for (String hash : history) {
				if (new StandardPasswordEncoder().matches(password, hash)){
					res = true;
					break;
				}
			}
		}		
		
		return res;
	}
	
	@Override
	public boolean checkPassword(User user, String pw){
		return standardPasswordEncoder.matches(pw, user.getPassword());
	}
	
	@Override
	public User getAuthenticatedUser(){
		TeaUserDetails userDetails = getUserDetails();
		if (userDetails == null) return null;
		User user = simpleDao.find(User.class, userDetails.getUserId());
		
		return user;
	}
	
	@Override
	public User getAuthenticatedUser(Authentication authentication){
		if (authentication == null) return null;
		
	   try {
		TeaUserDetails uDets = (TeaUserDetails) authentication.getPrincipal();
		   Integer userId = uDets.getUserId();
		   User user = simpleDao.find(User.class, userId);
			return user;
		} catch (Exception e) {
			// no valid authenticated user
			return null;
		}
	}
	
	@Override
	public TeaUserDetails getUserDetails(){
		try {
			TeaUserDetails userDetails = (TeaUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return userDetails;
		} catch (Exception e) {
			// no valid authenticated user
			return null;
		}
	}

	@Override
	public void updateCurrentAuthorities(Boolean mfaValid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TeaUserDetails userDetails = (TeaUserDetails)auth.getPrincipal();
		
		TeaUserDetails newDetails = teaUserDetailsService.updateUserDetailAuthorities(userDetails, mfaValid);
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(newDetails, auth.getCredentials(), newDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

}
