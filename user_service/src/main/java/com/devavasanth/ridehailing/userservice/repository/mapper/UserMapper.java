package com.devavasanth.ridehailing.userservice.repository.mapper;

import org.mapstruct.Mapper;

import com.devavasanth.ridehailing.userservice.dto.RegisterRequest;
import com.devavasanth.ridehailing.userservice.dto.UserResponse;
import com.devavasanth.ridehailing.userservice.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

	Users toEntity(RegisterRequest request);

	UserResponse toResponse(Users user);

}
