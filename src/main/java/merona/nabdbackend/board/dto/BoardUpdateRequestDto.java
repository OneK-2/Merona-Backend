package merona.nabdbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String contents;
    @Positive
    private Integer cost;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
}
