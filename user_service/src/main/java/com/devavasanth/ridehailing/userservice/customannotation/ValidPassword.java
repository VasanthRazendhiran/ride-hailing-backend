package com.devavasanth.ridehailing.userservice.customannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.devavasanth.ridehailing.userservice.validation.PasswordValidatior;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidatior.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

	String message() default "invalid password";

	Class<?>[] group() default {};

	Class<? extends Payload>[] payload() default {};
}
