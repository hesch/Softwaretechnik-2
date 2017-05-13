package de.randomerror.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
@AllArgsConstructor
public class Address {
    private String Street;
    private String number;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    public String toString() {
        return Street + " " + number + ", " + zipCode + " " + city;
    }
}
