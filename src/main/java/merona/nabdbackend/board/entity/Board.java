package merona.nabdbackend.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import merona.nabdbackend.BaseEntity;
import merona.nabdbackend.board.enums.State;
import merona.nabdbackend.user.entity.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String title;
    private String contents;

    @Enumerated(EnumType.STRING)
    private State state;

    //Address 추가 예정. 객체로 분리할지에 대한 추가 논의

    public Board(User user, String title, String contents){
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.state = State.REQUEST_WAITING;
    }

    public void update(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}
