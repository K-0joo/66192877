package com.skmnservice.board.controller;

import com.skmnservice.board.dto.BoardRequest;
import com.skmnservice.board.dto.BoardResponse;
import com.skmnservice.board.service.BoardService;
import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
