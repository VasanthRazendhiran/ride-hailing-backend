package com.devavasanth.ridehailing.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.constants.UserStatus;
import com.devavasanth.ridehailing.userservice.dto.LoginRequest;
import com.devavasanth.ridehailing.userservice.dto.LoginResponse;
import com.devavasanth.ridehailing.userservice.dto.RegisterRequest;
import com.devavasanth.ridehailing.userservice.dto.UserResponse;
import com.devavasanth.ridehailing.userservice.entity.Users;
import com.devavasanth.ridehailing.userservice.exception.DuplicateEmailException;
import com.devavasanth.ridehailing.userservice.exception.DuplicateMobileException;
import com.devavasanth.ridehailing.userservice.exception.PasswordMismatchExceptions;
import com.devavasanth.ridehailing.userservice.exception.UserNotFoundException;
import com.devavasanth.ridehailing.userservice.repository.UserRespository;
import com.devavasanth.ridehailing.userservice.repository.mapper.UserMapper;
import com.devavasanth.ridehailing.userservice.service.JwtService;
import com.devavasanth.ridehailing.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRespository userRespository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Autowired
	public UserServiceImpl(UserRespository userRespository, UserMapper userMapper, PasswordEncoder passwordEncoder,
			JwtService jwtService) {
		super();
		this.userRespository = userRespository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
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
	public LoginResponse login(LoginRequest request) {
		Users user = userRespository.findByEmail(request.email())
				.orElseThrow(() -> new UserNotFoundException("user not found with email: " + request.email()));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new PasswordMismatchExceptions("Invalid Credentials");
		}

		String token = jwtService.generateToken(user);

		return LoginResponse.builder().accessToken(token).tokenType("Bearer").expireIn(900L).build();
	}

}
