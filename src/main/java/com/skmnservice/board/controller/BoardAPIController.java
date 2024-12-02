package com.skmnservice.board.controller;

import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.entity.Board;
import com.skmnservice.board.service.BoardService;
import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.LoginResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
                            HttpSession session,
                            Model model){
        LoginResponse member = (LoginResponse) session.getAttribute("member");

        if(member == null){
            return "redirect:/api/member/login";
        }

        model.addAttribute("member", member);
        Page<Board> boardPage = boardService.getBoardList(page, size, keyword);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "html/board";
    }
}
