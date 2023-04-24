package merona.nabdbackend.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.auth.TokenDto;
import merona.nabdbackend.auth.TokenRequestDto;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.dto.LoginRequestDto;
import merona.nabdbackend.user.dto.SignUpRequestDto;
import merona.nabdbackend.user.entity.User;
import merona.nabdbackend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto requestDto) {
        log.info("TEST");
        String email = userService.signUp(requestDto);
        return ResponseEntity.ok().body(email);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto dto) {
        TokenDto tokenDto = userService.login(dto);
        return ResponseEntity.ok().body(tokenDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    // 사용자 작성 게시물 조회
    @GetMapping("/list")
    public ResponseEntity<List<Board>> viewUserPost(){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());

        List<Board> boards = userService.findBoardsByEmail(user);

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
}
