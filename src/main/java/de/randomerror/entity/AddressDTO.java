package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class AddressDTO extends AbstractEntity {
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    public String toString() {
        return street + " " + number + ", " + zipCode + " " + city;
    }

    public AddressDTO() {
        super("yk_address");

        addAttribute("street", (v) -> setStreet((String)v), this::getStreet, SqlType.TEXT);
        addAttribute("number", (v) -> setNumber((String)v), this::getNumber, SqlType.TEXT);
        addAttribute("zipCode", (v) -> setZipCode((String)v), this::getZipCode, SqlType.TEXT);
        addAttribute("city", (v) -> setCity((String)v), this::getCity, SqlType.TEXT);
        addAttribute("state", (v) -> setState((String)v), this::getState, SqlType.TEXT);
        addAttribute("country", (v) -> setCountry((String)v), this::getCountry, SqlType.TEXT);
    }

    public AddressDTO(Entity e) {
        this();

        this.fromEntity(e);
    }

    public AddressDTO(String street, String number, String zipCode, String city, String state, String country) {
        this();
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    static {
        JDBCConnector.registerEntity(AddressDTO.class, new AddressDTO().toEntity());
    }
}
