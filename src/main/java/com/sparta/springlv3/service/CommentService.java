package com.sparta.springlv3.service;

import com.sparta.springlv3.dto.CommentRequestDto;
import com.sparta.springlv3.dto.CommentResponseDto;
import com.sparta.springlv3.entity.Board;
import com.sparta.springlv3.entity.Comment;
import com.sparta.springlv3.entity.User;
import com.sparta.springlv3.repository.BoardRepository;
import com.sparta.springlv3.repository.CommentRepository;
import com.sparta.springlv3.status.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto addComment(Long boardId, CommentRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.save(new Comment(requestDto, user, board));

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(commentId);
        confirmUser(comment, user);
        comment.update(requestDto);
        return ResponseEntity.ok().body(new CommentResponseDto(comment)).getBody();
    }

    @Transactional
    public ResponseEntity<Message> deleteComment(Long commentId, User user) {
        Message message = new Message();

        Comment comment = findComment(commentId);
        confirmUser(comment, user);

        commentRepository.delete(comment);

        message.setMessage("삭제 완료");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    private Comment findComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
    }

    private void confirmUser(Comment comment, User user){
        if(!Objects.equals(comment.getUser().getId(), user.getId())){
            throw new IllegalArgumentException("사용자가 작성한 댓글이 아닙니다.");
        }
    }


}
