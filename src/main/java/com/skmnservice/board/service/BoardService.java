package com.skmnservice.board.service;

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
}
