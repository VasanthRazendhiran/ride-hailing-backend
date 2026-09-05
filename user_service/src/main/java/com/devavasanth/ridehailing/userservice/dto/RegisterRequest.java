package com.devavasanth.ridehailing.userservice.dto;

import com.devavasanth.ridehailing.userservice.constants.Roles;
import com.devavasanth.ridehailing.userservice.customannotation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(

		@NotNull(message = "first name can't be empty") String firstName,

		@NotNull(message = "last name can't be empty") String lastName,

		@Email String email,

		@Pattern(regexp = "^[0-9+\\s()-]{7,20}$", message = "Invalid mobile number format") String mobileNumber,

		@ValidPassword String password,

		String confirmPassword,

		Roles role) {

}
