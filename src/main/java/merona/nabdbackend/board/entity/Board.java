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
    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private State state;

    public Board(User user, String title, String contents, Address address) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.address = address;
        this.state = State.REQUEST_WAITING;
    }

    public void update(String title, String contents, Address address) {
        this.title = title;
        this.contents = contents;
        this.address = address;
    }

    public void updateState(State state) {
        this.state = state;
    }
}
