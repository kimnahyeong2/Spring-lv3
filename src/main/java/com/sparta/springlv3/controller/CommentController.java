package com.sparta.springlv3.controller;

import com.sparta.springlv3.dto.CommentRequestDto;
import com.sparta.springlv3.dto.CommentResponseDto;
import com.sparta.springlv3.security.UserDetailsImpl;
import com.sparta.springlv3.service.CommentService;
import com.sparta.springlv3.status.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Message> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(commentId, userDetails.getUser());
    }
}
