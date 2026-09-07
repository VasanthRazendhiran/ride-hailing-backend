package com.devavasanth.ridehailing.userservice.service;

import com.devavasanth.ridehailing.userservice.dto.LoginRequest;
import com.devavasanth.ridehailing.userservice.dto.LoginResponse;
import com.devavasanth.ridehailing.userservice.dto.RefreshTokenRequest;

public interface AuthService {

	LoginResponse login(LoginRequest request);

	LoginResponse refreshToken(RefreshTokenRequest request);

	void logout(RefreshTokenRequest request);
}
