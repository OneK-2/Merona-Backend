package merona.nabdbackend.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.board.dto.BoardSaveRequestDto;
import merona.nabdbackend.board.entity.Board;
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

}