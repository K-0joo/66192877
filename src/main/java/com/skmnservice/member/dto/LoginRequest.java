package com.skmnservice.member.dto;

public record LoginRequest(
        String id,
        String password
) {
}
