package merona.nabdbackend.exception;

public class FindUserWithUsernameNotFoundException extends RuntimeException{
    public FindUserWithUsernameNotFoundException() {
        super("해당 유저를 찾을 수 없습니다.");
    }
}
