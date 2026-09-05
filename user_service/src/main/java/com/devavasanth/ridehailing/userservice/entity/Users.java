package com.devavasanth.ridehailing.userservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.devavasanth.ridehailing.userservice.audit.AuditTableEntity;
import com.devavasanth.ridehailing.userservice.constants.Roles;
import com.devavasanth.ridehailing.userservice.constants.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users extends AuditTableEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "mobile_number", unique = true, nullable = false)
	private String mobileNumber;

	private String password;

	@Enumerated(EnumType.STRING)
	private Roles role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column(name = "email_verified")
	private Boolean emailVerified;

	@Column(name = "phone_verified")
	private Boolean phoneVerified;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;
}
