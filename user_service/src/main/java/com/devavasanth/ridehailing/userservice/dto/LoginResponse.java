package com.devavasanth.ridehailing.userservice.dto;

public record LoginResponse(String accessToken, String refresToken, String tokenType, Long expireIn) {

}
