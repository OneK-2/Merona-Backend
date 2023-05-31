package merona.nabdbackend.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.auth.TokenDto;
import merona.nabdbackend.auth.TokenRequestDto;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.dto.LoginRequestDto;
import merona.nabdbackend.user.dto.ModifyRequestDto;
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
    @ApiOperation(value="회원가입")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto requestDto) {
        log.info("TEST");
        String email = userService.signUp(requestDto);
        return ResponseEntity.ok().body(email);
    }

    @PostMapping("/login")
    @ApiOperation(value="로그인")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto dto) {
        TokenDto tokenDto = userService.login(dto);
        return ResponseEntity.ok().body(tokenDto);
    }

    @PostMapping("/reissue")
    @ApiOperation(value="토큰 재발급")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    // 사용자 작성 게시물 조회
    @GetMapping("/list")
    @ApiOperation(value="게시물 조회")
    public ResponseEntity<List<Board>> viewUserPost(){
        // 현재 세션 사용자의 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 아이디로 User 조회
        User user = userService.findUserByEmail(authentication.getName());

        List<Board> boards = userService.findBoardsByEmail(user);

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 사용자 아이디 중복 확인
    @GetMapping("/find/{email}")
    @ApiOperation(value="사용자 아이디 조회", notes="아이디 중복 확인")
    public ResponseEntity<Boolean> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }

    // 사용자 정보 가져오기
    @GetMapping("/info/{email}")
    @ApiOperation(value="사용자 정보 조회")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    // 사용자 정보 수정하기
    @PatchMapping("/modify/{email}")
    @ApiOperation(value="사용자 정보 수정")
    public ResponseEntity<String> modifyUser(@PathVariable String email, @RequestBody ModifyRequestDto modifyRequestDto){
        String updateUser = userService.updateUser(email, modifyRequestDto);
        return ResponseEntity.ok(updateUser);
    }
}
