package com.tea.landlordapp.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tea.landlordapp.domain.PasswordPolicy;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.service.SecurityService;

public class PasswordChangeValidator implements Validator {
	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserDao userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final User user = (User)target;
		Integer roleId = user.getRole().getId();
		PasswordPolicy policy = userDao.findPasswordPolicyByRoleId(roleId);

//		ValidationUtils.rejectIfEmpty(errors, "oldPassword", "error.invalid-oldpassword-blank");
		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.invalid-newpassword-blank");
		
		if (!StringUtils.equals(user.getNewPassword(), user.getRePassword())){
			errors.reject("newPassword", "error.invalid-newpassword-match");
		}
		
//		PasswordPolicy policy = user.getRole().getPasswordPolicy();
		if (policy != null){
			if (!securityService.passwordIsPolicyCompliant(user.getNewPassword(), policy)){
				errors.rejectValue("newPassword", "error.invalid-newpassword-complexity");
			}
			if (securityService.passwordInRecentHistory(user.getId(), user.getNewPassword(), policy)){
				errors.rejectValue("newPassword", "error.invalid-newpassword-repeat",new Object[]{new Integer(policy.getMinRememberedPassword())},null);
			}
		}

	}

}
