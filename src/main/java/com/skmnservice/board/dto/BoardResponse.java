package com.skmnservice.board.dto;

import java.util.UUID;

public record BoardResponse(
        String title,

        String author,
        String context,
        boolean filePrecence
) {
}
