package com.skmnservice.member.dto;

import java.util.UUID;

public record LoginResponse(
        UUID memberId,
        String id
) {
}
