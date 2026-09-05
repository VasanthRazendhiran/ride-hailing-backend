package com.devavasanth.ridehailing.userservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

	@Id
	@UuidGenerator
	private UUID id;

	private String token;

	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;

	private Boolean revoked;

	@ManyToOne
	private Users user;

}
