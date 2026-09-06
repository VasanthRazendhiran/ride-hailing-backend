package com.devavasanth.ridehailing.userservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devavasanth.ridehailing.userservice.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex) {
		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).code("EMAIL_ALREADY_EXISTS")
				.message(ex.getMessage()).build();

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(DuplicateMobileException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateMobileNumber(DuplicateMobileException ex) {
		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.code("MOBILENUMBER_ALREADY_EXITS").message(ex.getMessage()).build();

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(PasswordMismatchExceptions.class)
	public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchExceptions ex) {
		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).code("PASSWORD_MISMATCH")
				.message(ex.getMessage()).build();

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).code("USER_NOT_FOUND")
				.message(ex.getMessage()).build();

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}

		return ResponseEntity.badRequest().body(errors);
	}

}
