package com.skmnservice.board.repository;

import com.skmnservice.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    // @Query("SELECT b FROM Board b JOIN FETCH b.member m WHERE b.title LIKE %:keyword% OR m.id LIKE %:keyword%")
    Page<Board> findByTitleOrAuthorId(String title, String authorId, Pageable pageable);
}
