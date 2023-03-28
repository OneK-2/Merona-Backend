package merona.nabdbackend.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import merona.nabdbackend.user.dto.SignUpRequestDto;
import merona.nabdbackend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
