package com.skmnservice.board.service;

import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.entity.Board;
import com.skmnservice.global.error.ErrorCode;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.member.entity.Member;
import com.skmnservice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BoardResponse write(BoardRequest requestDto){
        // 작성자 정보 조회
        Member author = memberRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 게시글 생성
        Board board = Board.builder()
                .title(requestDto.title())
                .context(requestDto.context())
                .author(author)
                .filePrecence(requestDto.filePrecence())
                .boardCreatedTime(LocalDateTime.now())
                .build();

        // 게시글 저장
        board = boardRepository.save(board);

        // 응답 DTO 생성
        return new BoardResponse(board.getTitle(), board.getContext(), author.getId(), board.getFiles().isEmpty());
    }
}
