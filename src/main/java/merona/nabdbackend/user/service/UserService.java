package merona.nabdbackend.user.service;

import lombok.RequiredArgsConstructor;
import merona.nabdbackend.user.dto.SignUpRequestDto;
import merona.nabdbackend.user.entity.User;
import merona.nabdbackend.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public String signUp(SignUpRequestDto requestDto) {
        Optional<User> userByEmail = userRepository.findUserByEmail(requestDto.getEmail());
        if (userByEmail.isPresent()) {
            throw new RuntimeException();   //TODO 이미 존재합니다 exception
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePW = encoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodePW);
        User user = requestDto.userFromDto();
        userRepository.save(user);

        return user.getEmail();
    }

}
