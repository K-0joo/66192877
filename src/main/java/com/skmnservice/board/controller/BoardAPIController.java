package com.skmnservice.board.controller;

import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.entity.Board;
import com.skmnservice.board.service.BoardService;
import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.LoginResponse;
import com.skmnservice.member.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/board")
public class BoardAPIController {
    private final BoardService boardService;

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<ResponseDto> write(@RequestBody BoardRequest requestDto){
        BoardResponse responseDto = boardService.write(requestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.BOARD_SUCCESS, responseDto));
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
}
