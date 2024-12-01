package com.skmnservice.board.entity;

import com.skmnservice.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "member")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "board_id", nullable = false)
    private UUID boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // 외래 키 설정
    private Member author; // 작성자

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "context", columnDefinition = "TEXT")
    private String context;

    @Column(name = "hits", nullable = false)
    private long hits = 0;

    @Column(name = "file_precence", nullable = false)
    private boolean filePrecence;

    @Column(name = "board_created_time", nullable = false)
    private LocalDateTime boardCreatedTime;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files; // 첨부 파일 목록
}
