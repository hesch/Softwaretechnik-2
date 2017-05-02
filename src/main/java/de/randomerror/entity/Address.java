package de.randomerror.entity;

import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Address {
    private String Street;
    private String number;
    private String company;
    private String zipCode;
    private String city;
    private String province;
    private String country;
}
