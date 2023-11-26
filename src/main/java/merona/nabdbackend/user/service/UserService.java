package merona.nabdbackend.user.service;

import lombok.RequiredArgsConstructor;
import merona.nabdbackend.auth.*;
import merona.nabdbackend.auth.token.RefreshToken;
import merona.nabdbackend.auth.token.RefreshTokenRepository;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.dto.LoginRequestDto;
import merona.nabdbackend.user.dto.ModifyRequestDto;
import merona.nabdbackend.user.dto.SignUpRequestDto;
import merona.nabdbackend.user.entity.Member;
import merona.nabdbackend.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(SignUpRequestDto requestDto) {
        Optional<Member> userByEmail = userRepository.findUserByEmail(requestDto.getEmail());
        if (userByEmail.isPresent()) {
            throw new RuntimeException();   //TODO 이미 존재합니다 exception
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePW = encoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodePW);
        Member member = requestDto.userFromDto();
        userRepository.save(member);

        return member.getEmail();
    }

    @Transactional
    public TokenDto login(LoginRequestDto dto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 생성
        TokenDto tokenDto = jwtProvider.generateToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        // 1. Refresh Token 검증
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = jwtProvider.generateToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }

    public Member findUserByEmail(String userEmail) {
        Optional<Member> userByEmail = userRepository.findUserByEmail(userEmail);
        Member member = userByEmail.get();
        return member;
    }

    public boolean checkEmailDuplicate(String email){
        Optional<Member> userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail.isPresent()) {
            return true;   //TODO 이미 존재합니다 exception
        }
        else{
            return false;
        }
    }

    public List<Board> findBoardsByEmail(Member member) {
        return userRepository.findById(member.getId()).get().getBoards();
    }

    // 사용자 정보 수정
    public String updateUser(String email, ModifyRequestDto modifyRequestDto){
        Member member = userRepository.findUserByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 회원이 없습니다. email=" + email));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePW = encoder.encode(modifyRequestDto.getPassword());
        modifyRequestDto.setPassword(encodePW);
        member.update(modifyRequestDto.getEmail(), modifyRequestDto.getName(), modifyRequestDto.getPassword());
        return email;
    }

}
