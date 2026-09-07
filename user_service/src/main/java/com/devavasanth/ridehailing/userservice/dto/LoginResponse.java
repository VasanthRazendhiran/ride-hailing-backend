package com.devavasanth.ridehailing.userservice.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String accessToken, String refreshToken, String tokenType, Long expireIn) {

}
