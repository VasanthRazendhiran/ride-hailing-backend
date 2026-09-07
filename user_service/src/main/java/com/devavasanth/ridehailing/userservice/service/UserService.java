package com.devavasanth.ridehailing.userservice.service;

import com.devavasanth.ridehailing.userservice.dto.RegisterRequest;
import com.devavasanth.ridehailing.userservice.dto.UserResponse;

public interface UserService {

	UserResponse register(RegisterRequest request);

	UserResponse getCurrentUser(String email);
}
