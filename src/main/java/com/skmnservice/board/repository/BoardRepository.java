package com.skmnservice.board.repository;

import com.skmnservice.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    Page<Board> findByTitleOrAuthorId(String title, String authorId, Pageable pageable);
}
