package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Customer extends AbstractEntity {
    private String name;
    private Address address;
    private String email;
    private String phoneNumber;
    private int id;

    public Customer() {
        super("yk_customer");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("email", (v) -> setEmail((String)v), this::getEmail, SqlType.TEXT);
        addAttribute("phoneNumber", (v) -> setPhoneNumber((String)v), this::getPhoneNumber, SqlType.TEXT);

    }

    public Customer(String name, Address address, String email, String phoneNumber, int id) {
        this();
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    static {
        JDBCConnector.registerEntity(Customer.class, new Customer().toEntity());
    }
}
