package com.devavasanth.ridehailing.userservice.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.entity.Users;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String secretkey;

	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;

	private Key getSignInKey() {
		byte[] keyByte = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyByte);
	}

	public String generateToken(Users user) {
		return Jwts.builder().subject(user.getEmail()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(getSignInKey()).compact();
	}
}
