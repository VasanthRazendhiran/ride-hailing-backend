package com.devavasanth.ridehailing.userservice.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver_profile")
public class DriverProfile {

	@Id
	@UuidGenerator
	private UUID id;

	@OneToOne
	private Users user;

	@Column(name = "vehicle_number")
	private String vehicleNumber;

	@Column(name = "vehicle_type")
	private String vehicleType;

	@Column(name = "driving_license_Number")
	private String drivingLicenseNumber;

	private Double rating;
}
