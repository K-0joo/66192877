package com.skmnservice.board.controller;

import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.entity.Board;
import com.skmnservice.board.service.BoardService;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.entity.Member;
import com.skmnservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/board")
public class BoardAPIController {
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<ResponseDto> write(@RequestParam("title") String title,
                                             @RequestParam("context") String context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).body(ResponseDto.of(ResponseCode.LOGIN_FAIL, "로그인이 필요합니다."));
        }

        // Principal에서 ID 추출
        String username = authentication.getName(); // Spring Security의 기본 username

        // Member 정보 조회
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        UUID memberId = member.getMemberId();

        // BoardRequest 생성 후 저장
        BoardRequest requestDto = new BoardRequest(title, memberId, context);
        BoardResponse responseDto = boardService.write(requestDto);

        return ResponseEntity.ok(ResponseDto.of(ResponseCode.BOARD_SUCCESS, responseDto));
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("upload") MultipartFile file) {
        try {
            // 파일 저장 및 URL 생성
            String fileUrl = boardService.saveFile(file);

            // CKEditor 응답 형식
            return ResponseEntity.ok(Map.of("uploaded", true, "url", fileUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("uploaded", false, "error", Map.of("message", "파일 업로드 실패")));
        }
    }

    @GetMapping
    public String boardList(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String keyword,
                            Model model){

        // Spring Security에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("member", authentication.getPrincipal());
        } else {
            model.addAttribute("member", null);
            return "redirect:/api/member/login";
        }

        try {
            Page<Board> boardPage = boardService.getBoardList(page, size, keyword);
            model.addAttribute("boardPage", boardPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", keyword);   // totalPages를 Model에 추가
            model.addAttribute("totalPages", boardPage.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return "error/500"; // 오류 페이지로 리다이렉트
        }

        return "html/board";
    }

    @GetMapping("/write")
    public String boardDetail(Model model){

        // Spring Security에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("member", authentication.getPrincipal());
        } else {
            model.addAttribute("member", null);
            return "redirect:/api/member/login";
        }

        return "html/boardWrite";
    }

    @GetMapping("/{boardId}")
    public String getBoardDetail(@PathVariable UUID boardId, Model model) {
        try {
            BoardResponse board = boardService.getBoardById(boardId);
            model.addAttribute("board", board);
        } catch (NotFoundException e) {
            return "error/404"; // 게시글이 없을 경우 404 페이지로 이동
        }
        return "html/boardDetail"; // 상세 페이지 HTML 파일
    }

}
