package merona.nabdbackend.board.dto;

import lombok.Getter;
import merona.nabdbackend.board.entity.Address;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.board.enums.State;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Address address;
    private Integer cost;
    private State state;

    private String email;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.address = board.getAddress();
        this.cost = board.getCost();
        this.state = board.getState();
        this.email = board.getUser().getEmail();
    }
}
