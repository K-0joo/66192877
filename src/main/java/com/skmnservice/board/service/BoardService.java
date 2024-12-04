package com.skmnservice.board.service;

import com.skmnservice.board.dto.BoardDetailResponse;
import com.skmnservice.board.dto.BoardEditRequest;
import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.entity.Board;
import com.skmnservice.board.entity.File;
import com.skmnservice.board.repository.BoardRepository;
import com.skmnservice.file.repository.FileRepository;
import com.skmnservice.global.error.ErrorCode;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.member.entity.Member;
import com.skmnservice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;

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
                .boardCreatedTime(LocalDateTime.now())
                .build();

        // 게시글 저장
        board = boardRepository.save(board);

        // 응답 DTO 생성
        return new BoardResponse(board.getTitle(), board.getContext(), author.getId());
    }

    @Transactional
    public Page<Board> getBoardList(int page, int size, String keyword){
        PageRequest pageable = PageRequest.of(page, size, Sort.by("boardCreatedTime").descending());
        return boardRepository.findByTitleOrAuthorId(keyword, keyword, pageable);
    }

    @Transactional
    public String saveFile(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String saveName = UUID.randomUUID() + "_" + originalName;
            Path path = Paths.get("file-storage/" + saveName);

            Files.createDirectories(path.getParent());
            file.transferTo(path.toFile());

            return "/file-storage/" + saveName; // 저장된 파일의 URL 반환
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생" + e.getMessage(), e);
        }
    }

    @Transactional
    public BoardDetailResponse getBoardById(UUID boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        return new BoardDetailResponse(board.getBoardId() ,board.getTitle(), board.getAuthor().getId(), board.getContext(), board.getHits(), board.getBoardCreatedTime());
    }

    @Transactional
    public void deleteBoard(UUID boardId, UUID memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        // 게시글 작성자 검증
        if (!board.getAuthor().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다.");
        }

        // 게시글 삭제
        boardRepository.delete(board);
    }

    public void editBoard(UUID boardId, UUID memberId, BoardEditRequest editRequest) {
        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        // 게시글 작성자 검증
        if (!board.getAuthor().getMemberId().equals(memberId)) {
            throw new AccessDeniedException("게시글 수정 권한이 없습니다.");
        }

        // 게시글 내용 수정
        board.setTitle(editRequest.title());
        board.setContext(editRequest.context());

        // 게시글 저장
        boardRepository.save(board);
    }
}
