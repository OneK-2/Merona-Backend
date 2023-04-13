package merona.nabdbackend.auth;

import lombok.RequiredArgsConstructor;
import merona.nabdbackend.exception.FindUserWithUsernameNotFoundException;
import merona.nabdbackend.user.entity.User;
import merona.nabdbackend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(FindUserWithUsernameNotFoundException::new);
        return new CustomUserDetails(user);
    }
}
