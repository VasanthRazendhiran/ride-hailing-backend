package com.devavasanth.ridehailing.userservice.service;

import com.devavasanth.ridehailing.userservice.dto.LoginRequest;
import com.devavasanth.ridehailing.userservice.dto.LoginResponse;
import com.devavasanth.ridehailing.userservice.dto.RegisterRequest;
import com.devavasanth.ridehailing.userservice.dto.UserResponse;

public interface UserService {

	UserResponse register(RegisterRequest request);

	LoginResponse login(LoginRequest request);
}
