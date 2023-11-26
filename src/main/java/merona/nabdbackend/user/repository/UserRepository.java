package merona.nabdbackend.user.repository;

import merona.nabdbackend.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findUserByEmail(String email);
}
