package merona.nabdbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import merona.nabdbackend.board.entity.Address;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.entity.User;

import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveRequestDto {
    private User user;
    private String title;
    private String contents;
    @Positive
    private Integer cost;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
    // 주소

    public Board boardFormDto() {
        return new Board(user, title, contents, cost, new Address(zipcode, streetAddress, detailAddress));
    }

    public void setUser(User user) {
        this.user = user;
    }

}
