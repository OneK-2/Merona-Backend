package merona.nabdbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String contents;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
}
