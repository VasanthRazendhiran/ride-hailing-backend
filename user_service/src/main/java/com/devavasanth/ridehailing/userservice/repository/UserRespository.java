package com.devavasanth.ridehailing.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devavasanth.ridehailing.userservice.entity.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Repository
public interface UserRespository extends JpaRepository<Users, UUID> {

	boolean existsByEmail(@Email String email);

	boolean existsByMobileNumber(
			@Pattern(regexp = "^[0-9+\\s()-]{7,20}$", message = "Invalid mobile number format") String mobileNumber);

	Optional<Users> findByEmail(String email);

}
