package de.randomerror.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
@AllArgsConstructor
public class Customer {
    private String name;
    private Address address;
    private String email;
    private String phoneNumber;
}
