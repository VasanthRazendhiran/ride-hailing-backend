package com.devavasanth.ridehailing.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.entity.Users;
import com.devavasanth.ridehailing.userservice.repository.UserRespository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	private final UserRespository userRespository;

	@Autowired
	public CustomerUserDetailsService(UserRespository userRespository) {
		super();
		this.userRespository = userRespository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRespository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));

		return User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getRole().name())
				.build();
	}

}
