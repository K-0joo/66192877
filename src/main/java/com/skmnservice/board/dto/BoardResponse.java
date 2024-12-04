package com.skmnservice.board.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BoardResponse(
        UUID boardId,         // 게시글 ID
        String title,         // 제목
        String author,        // 작성자 이름
        String context,       // 내용
        LocalDateTime boardCreatedTime, // 작성 날짜
        int hits              // 조회수
) {
}
