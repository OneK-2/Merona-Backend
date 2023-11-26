package merona.nabdbackend.user.dto;

import lombok.Data;
import merona.nabdbackend.user.entity.Member;

@Data
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    public Member userFromDto() {
        return new Member(email, name, password, phoneNumber);
    }
}
