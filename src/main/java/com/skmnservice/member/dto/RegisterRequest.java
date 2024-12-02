package com.skmnservice.member.dto;

public record RegisterRequest(
        String id,
        String password,
        String name
) {
}
