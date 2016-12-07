package com.tea.landlordapp.beans;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.tea.landlordapp.domain.LoginLog;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.UserDao;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
	
	@Autowired
	SimpleDao simpleDao;
	
	@Autowired
	UserDao userDao;
	
	protected Log logger = LogFactory.getLog(this.getClass());
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
 
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
 
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
    	
		
		TeaUserDetails uDets = (TeaUserDetails)authentication.getPrincipal();
		User user = simpleDao.find(User.class, uDets.getUserId());
//		User user = userDao.findUser(uDets.getUserId());
		user.setLastLogin(new Timestamp((new Date()).getTime()));
		user.setLoginFailCount(0);
		user.setLockoutUntil(null);
		user = simpleDao.merge(user);
//		LoginLog log = new LoginLog(user,"Successful Login");
//		simpleDao.persist(log);

		
//		 need to uncomment changepwd later on
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (StringUtils.equalsIgnoreCase(authority.getAuthority(), "must.change.password")){
				return "/changepwd.htm";
			}
		}
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (StringUtils.equalsIgnoreCase(authority.getAuthority(), "dual.authentication.required")){
				return "/duologin";
			}
		}
//		for (GrantedAuthority authority : authentication.getAuthorities()) {
//			if (StringUtils.equalsIgnoreCase(authority.getAuthority(), "must.setup.questions")){
//				return "/questions.htm";
//			}
//		}
//		for (GrantedAuthority authority : authentication.getAuthorities()) {
//			if (StringUtils.equalsIgnoreCase(authority.getAuthority(), "security.question.required")){
//				return "/securitycheck.htm";
//			}
//		}

		return "/home.htm";
	
    
//        boolean isUser = false;
//        boolean isAdmin = false;
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
//                isUser = true;
//                break;
//            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//                isAdmin = true;
//                break;
//            }
//        }
// 
//        if (isUser) {
//            return "/home.html";
//        } else if (isAdmin) {
//            return "/console.html";
//        } else {
//            throw new IllegalStateException();
//        }
    }
 
   
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
