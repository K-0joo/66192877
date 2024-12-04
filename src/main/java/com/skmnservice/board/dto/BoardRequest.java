package com.skmnservice.board.dto;

import java.util.UUID;

public record BoardRequest(
        String title,

        UUID memberId,
        String context
) {
}
