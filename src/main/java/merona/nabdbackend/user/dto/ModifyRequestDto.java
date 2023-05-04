package merona.nabdbackend.user.dto;

import lombok.Data;

@Data
public class ModifyRequestDto {
    private String email;
    private String name;
    private String password;
}
