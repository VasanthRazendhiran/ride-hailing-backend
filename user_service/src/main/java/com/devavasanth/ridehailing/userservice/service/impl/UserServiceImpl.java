package com.devavasanth.ridehailing.userservice.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.constants.UserStatus;
import com.devavasanth.ridehailing.userservice.dto.LoginRequest;
import com.devavasanth.ridehailing.userservice.dto.LoginResponse;
import com.devavasanth.ridehailing.userservice.dto.RegisterRequest;
import com.devavasanth.ridehailing.userservice.dto.UserResponse;
import com.devavasanth.ridehailing.userservice.entity.RefreshToken;
import com.devavasanth.ridehailing.userservice.entity.Users;
import com.devavasanth.ridehailing.userservice.exception.DuplicateEmailException;
import com.devavasanth.ridehailing.userservice.exception.DuplicateMobileException;
import com.devavasanth.ridehailing.userservice.exception.PasswordMismatchExceptions;
import com.devavasanth.ridehailing.userservice.exception.UserNotFoundException;
import com.devavasanth.ridehailing.userservice.repository.RefreshTokenRepository;
import com.devavasanth.ridehailing.userservice.repository.UserRespository;
import com.devavasanth.ridehailing.userservice.repository.mapper.UserMapper;
import com.devavasanth.ridehailing.userservice.service.UserService;
import com.devavasanth.ridehailing.userservice.service.handler.JwtService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRespository userRespository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final RefreshTokenRepository refreshTokenRepository;

	@Autowired
	public UserServiceImpl(UserRespository userRespository, UserMapper userMapper, PasswordEncoder passwordEncoder,
			JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
		super();
		this.userRespository = userRespository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public UserResponse register(RegisterRequest request) {

		// validation
		if (userRespository.existsByEmail(request.email())) {
			throw new DuplicateEmailException("Email already registered");
		}

		if (userRespository.existsByMobileNumber(request.mobileNumber())) {
			throw new DuplicateMobileException("Mobile number already registered");
		}

		if (!request.password().equals(request.confirmPassword())) {
			throw new PasswordMismatchExceptions("Password do not match");
		}

		// convert dto to entity
		Users user = userMapper.toEntity(request);

		// encrypt password
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setStatus(UserStatus.ACTIVE);
		user.setEmailVerified(false);
		user.setPhoneVerified(false);

		// save
		user = userRespository.save(user);

		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse getCurrentUser(String email) {
		Users user = userRespository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
		return userMapper.toResponse(user);
	}

}
