package merona.nabdbackend.board.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String zipcode;
    private String streetAddress;
    private String detailAddress;

    protected Address() {
    }

    public Address(String zipcode, String streetAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }
}
