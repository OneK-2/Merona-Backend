package merona.nabdbackend.user.dto;

import lombok.Data;
import merona.nabdbackend.user.entity.User;

@Data
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;
    private String phoneNumber;

    public User userFromDto() {
        return new User(email, name, password, phoneNumber);
    }
}
