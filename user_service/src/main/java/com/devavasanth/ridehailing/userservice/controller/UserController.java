package com.devavasanth.ridehailing.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devavasanth.ridehailing.userservice.dto.UserResponse;
import com.devavasanth.ridehailing.userservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public UserResponse me(Authentication authentication) {
		String email = authentication.getName();

		return userService.getCurrentUser(email);
	}

}
