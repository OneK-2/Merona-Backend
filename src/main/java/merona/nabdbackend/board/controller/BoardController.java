package merona.nabdbackend.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.board.dto.BoardSaveRequestDto;
import merona.nabdbackend.board.dto.BoardUpdateRequestDto;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.board.enums.State;
import merona.nabdbackend.board.service.BoardService;
import merona.nabdbackend.user.entity.User;
import merona.nabdbackend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    // 게시물 작성
    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());

        // 게시글 저장
        boardService.save(boardSaveRequestDto, user);

        return ResponseEntity.ok().body(boardSaveRequestDto.getTitle());
    }

    // 게시물 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<Board>> viewPost(){
        List<Board> boards= boardService.findAll();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 게시글 세부 내용 조회
    @GetMapping("/list/{id}")
    public ResponseEntity<Board> viewDetailPost(@PathVariable Long id){
        Board board = boardService.findById(id).get();
        return ResponseEntity.ok().body(board);
    }

    // 게시글 삭제
    @DeleteMapping("/list/{id}/delete")
    public ResponseEntity<Long> deletePost(@PathVariable Long id){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());
        Board board = boardService.findById(id).get();

        // 작성한 사용자만 지울 수 있음
        if (user.getId().equals(board.getUser().getId())){
            boardService.deleteById(id);
        }
        return ResponseEntity.ok().body(id);
    }

    // 게시글 수정
    @PatchMapping("/list/{id}/update")
    public ResponseEntity<Long> updatePost(@PathVariable Long id, @RequestBody BoardUpdateRequestDto updateDto){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());
        Board board = boardService.findById(id).get();

        // 작성한 사용자만 수정할 수 있음
        if (user.getId().equals(board.getUser().getId())){
            Long savedBoardId = boardService.updateBoard(id, updateDto);
        }
        else{
            throw new RuntimeException("수정할 수 없습니다.");
        }
        return ResponseEntity.ok().body(id);
    }

    // 게시글 상태 진행중
    @PatchMapping("/list/{id}/ongoing")
    public ResponseEntity<Long> updateStateOngoing(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        Board board = boardService.findById(id).get();

        // 작성한 사용자만 수정할 수 있음
        if (user.getId().equals(board.getUser().getId())){
            boardService.updateState(id, State.REQUEST_ON_GOING);
        }
        else{
            throw new RuntimeException("수정할 수 없습니다.");
        }
        return ResponseEntity.ok().body(id);
    }

    // 게시글 상태 완료
    @PatchMapping("/list/{id}/completed")
    public ResponseEntity<Long> updateStateCompleted(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        Board board = boardService.findById(id).get();

        // 작성한 사용자만 수정할 수 있음
        if (user.getId().equals(board.getUser().getId())){
            boardService.updateState(id,State.REQUEST_COMPLETE);
        }
        else{
            throw new RuntimeException("수정할 수 없습니다.");
        }
        return ResponseEntity.ok().body(id);
    }

}