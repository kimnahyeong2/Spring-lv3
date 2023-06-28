package com.sparta.springlv3.controller;

import com.sparta.springlv2.dto.BoardRequestDto;
import com.sparta.springlv2.dto.BoardResponseDto;
import com.sparta.springlv2.security.UserDetailsImpl;
import com.sparta.springlv2.service.BoardService;
import com.sparta.springlv2.status.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j

public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/feed")
    public List<BoardResponseDto.BoardReadResponseDto> getBoards(){
        return boardService.getBoards();
    }

    @PostMapping("/feed")
    public BoardResponseDto.BoardBasicResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    @GetMapping("/feed/{id}")
    public BoardResponseDto.BoardReadResponseDto getSelectBoards(@PathVariable Long id){
        return boardService.getSelectBoards(id);
    }


    @PutMapping("/feed/{id}")
    public BoardResponseDto.BoardReadResponseDto updateBoard(
            @PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/feed/{id}")
    public ResponseEntity<Message> deleteMemo(
            @PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return boardService.deleteBoard(id, requestDto, userDetails.getUser());
    }
}
