package merona.nabdbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import merona.nabdbackend.board.entity.Address;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.user.entity.Member;

import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveRequestDto {
    private Member member;
    private String title;
    private String contents;
    @Positive
    private Integer cost;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
    // 주소

    public Board boardFormDto() {
        return new Board(member, title, contents, cost, new Address(zipcode, streetAddress, detailAddress));
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
