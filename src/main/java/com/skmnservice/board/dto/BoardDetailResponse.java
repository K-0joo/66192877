package com.skmnservice.board.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BoardDetailResponse(
        UUID boardId,
        String title,

        String author,
        String context,
        long hits,
        LocalDateTime boardCreatedTime

) {
}
