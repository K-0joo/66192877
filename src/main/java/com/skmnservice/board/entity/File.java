package com.skmnservice.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 Auto Increment
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY) // 게시글과 다대일 관계 설정
    @JoinColumn(name = "board_id", nullable = false)
    private Board board; // 첨부 파일이 속한 게시글

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName; // 원본 파일 이름

    @Column(name = "save_name", nullable = false, length = 40)
    private String saveName; // 서버에 저장된 파일 이름

    @Column(name = "size", nullable = false)
    private long size; // 파일 크기 (바이트)

    @Column(name = "file_created_time", nullable = false)
    private LocalDateTime fileCreatedTime; // 파일 업로드 시간
}
