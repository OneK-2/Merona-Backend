package merona.nabdbackend.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.board.dto.BoardSaveRequestDto;
import merona.nabdbackend.board.service.BoardService;
import merona.nabdbackend.user.entity.User;
import merona.nabdbackend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    // 게시물 작성
    @PostMapping("/board/save")
    public ResponseEntity<String> savePost(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());

        // 게시글 저장
        boardService.save(boardSaveRequestDto, user);

        return ResponseEntity.ok().body(boardSaveRequestDto.getTitle());
    }

}