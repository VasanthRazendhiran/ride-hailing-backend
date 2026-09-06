package com.devavasanth.ridehailing.userservice.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.devavasanth.ridehailing.userservice.entity.Users;

import io.jsonwebtoken.Claims;
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

	/**
	 * purpose:- jwt token -> read payload -> get subject, expiry, role etc..
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * purpose:- extract email from token {"sub":"***@gmail.com"}
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	/**
	 * purpose:- check if jwt is expired
	 */
	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String userName = extractUsername(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

}
