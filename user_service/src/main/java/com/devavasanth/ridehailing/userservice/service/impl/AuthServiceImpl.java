package com.devavasanth.ridehailing.userservice.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.dto.LoginRequest;
import com.devavasanth.ridehailing.userservice.dto.LoginResponse;
import com.devavasanth.ridehailing.userservice.dto.RefreshTokenRequest;
import com.devavasanth.ridehailing.userservice.entity.RefreshToken;
import com.devavasanth.ridehailing.userservice.entity.Users;
import com.devavasanth.ridehailing.userservice.exception.PasswordMismatchExceptions;
import com.devavasanth.ridehailing.userservice.exception.UserNotFoundException;
import com.devavasanth.ridehailing.userservice.exception.ValidateTokenException;
import com.devavasanth.ridehailing.userservice.repository.RefreshTokenRepository;
import com.devavasanth.ridehailing.userservice.repository.UserRespository;
import com.devavasanth.ridehailing.userservice.service.AuthService;
import com.devavasanth.ridehailing.userservice.service.handler.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRespository userRespository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Autowired
	public AuthServiceImpl(UserRespository userRespository, RefreshTokenRepository refreshTokenRepository,
			PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.userRespository = userRespository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		Users user = userRespository.findByEmail(request.email())
				.orElseThrow(() -> new UserNotFoundException("user not found with email: " + request.email()));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new PasswordMismatchExceptions("Invalid Credentials");
		}

		String token = jwtService.generateToken(user);

		String refreshToken = UUID.randomUUID().toString();

		RefreshToken tokenEntity = RefreshToken.builder().token(refreshToken)
				.expiryDate(LocalDateTime.now().plusDays(7)).revoked(false).user(user).build();

		refreshTokenRepository.save(tokenEntity);

		return LoginResponse.builder().accessToken(token).refreshToken(refreshToken).tokenType("Bearer").expireIn(900L)
				.build();
	}

	@Override
	public LoginResponse refreshToken(RefreshTokenRequest request) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
				.orElseThrow(() -> new ValidateTokenException("Invalid refresh token"));

		if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
			throw new ValidateTokenException("refresh token revoked");
		}

		if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new ValidateTokenException("refresh token expires");
		}

		Users user = refreshToken.getUser();
		String accessToken = jwtService.generateToken(user);

		return LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getToken())
				.tokenType("Bearer").expireIn(900L).build();
	}

	@Override
	public void logout(RefreshTokenRequest request) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
				.orElseThrow(() -> new RuntimeException("Invalid refresh token"));
		refreshToken.setRevoked(true);
		refreshTokenRepository.save(refreshToken);
	}

}
