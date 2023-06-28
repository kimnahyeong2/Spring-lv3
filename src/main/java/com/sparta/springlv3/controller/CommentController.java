package com.sparta.springlv3.controller;

import com.sparta.springlv3.dto.CommentRequestDto;
import com.sparta.springlv3.dto.CommentResponseDto;
import com.sparta.springlv3.security.UserDetailsImpl;
import com.sparta.springlv3.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardId}")
    public CommentResponseDto addComment(@PathVariable Long boardId, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return commentService.addComment(boardId, requestDto, userDetails.getUser());
    }
}
