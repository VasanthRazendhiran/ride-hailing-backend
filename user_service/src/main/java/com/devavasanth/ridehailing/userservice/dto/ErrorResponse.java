package com.devavasanth.ridehailing.userservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timestamp;
	private String code;
	private String message;
}
