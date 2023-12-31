package com.sparta.springlv3.service;

import com.sparta.springlv3.dto.BoardRequestDto;
import com.sparta.springlv3.dto.BoardResponseDto;
import com.sparta.springlv3.entity.Board;
import com.sparta.springlv3.entity.Comment;
import com.sparta.springlv3.entity.User;
import com.sparta.springlv3.entity.UserRoleEnum;
import com.sparta.springlv3.repository.BoardRepository;
import com.sparta.springlv3.status.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardResponseDto.BoardReadResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto.BoardReadResponseDto::new).toList();
    }

    public BoardResponseDto.BoardBasicResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto.BoardBasicResponseDto(board);
    }

    public BoardResponseDto.BoardReadResponseDto getSelectBoards(Long id) {
        Board board = findBoard(id);
        return ResponseEntity.ok().body(new BoardResponseDto.BoardReadResponseDto(board)).getBody();
    }

    @Transactional
    public BoardResponseDto.BoardReadResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
        Board board = findBoard(id);
        confirmUser(board, user);
        board.update(requestDto);
        return ResponseEntity.ok().body(new BoardResponseDto.BoardReadResponseDto(board)).getBody();
    }

    public ResponseEntity<Message> deleteBoard(Long id, BoardRequestDto requestDto, User user){
        Message message = new Message();
        
        Board board = findBoard(id);
        confirmUser(board, user);
        
        boardRepository.delete(board);
        message.setMessage("삭제 완료");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    private Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("존재하지 않습니다")
        );
    }

    private void confirmUser(Board board, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        if (userRoleEnum == UserRoleEnum.USER && !Objects.equals(board.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("사용자가 작성한 게시물이 아닙니다.");
        }
    }

}