package com.devavasanth.ridehailing.userservice.dto;

import java.util.UUID;

import com.devavasanth.ridehailing.userservice.constants.Roles;
import com.devavasanth.ridehailing.userservice.constants.UserStatus;

public record UserResponse(UUID id, String firstName, String lastName, String email, String mobileNumber, Roles role,
		UserStatus status) {

}
