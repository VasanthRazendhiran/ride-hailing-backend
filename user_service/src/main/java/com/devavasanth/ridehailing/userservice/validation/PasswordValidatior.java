package com.devavasanth.ridehailing.userservice.validation;

import com.devavasanth.ridehailing.userservice.customannotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidatior implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {

		if (password == null)
			return false;

		return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
				&& password.matches(".*\\d.*") && password.matches(".*[@$!%?&].*");
	}

}
