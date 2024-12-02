package com.skmnservice.member.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID memberId,
        String id,
        String name
) {
}
