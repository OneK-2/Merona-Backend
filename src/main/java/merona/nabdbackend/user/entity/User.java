package merona.nabdbackend.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import merona.nabdbackend.BaseEntity;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

    //Address 추가 예정. 객체로 분리할지에 대한 추가 논의

    public User(String email, String name, String password, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = Role.ROLE_USER;
    }

    public void update(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
