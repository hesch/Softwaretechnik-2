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
public class DeliveryHuman extends AbstractEntity {
    private int id;
    private String name;
    private Address address;
    private String email;
    private String phoneNumber;
    
    public DeliveryHuman() {
        super("vk_delivery_human");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("email", (v) -> setEmail((String)v), this::getEmail, SqlType.TEXT);
        addAttribute("phoneNumber", (v) -> setPhoneNumber((String)v), this::getPhoneNumber, SqlType.TEXT);
    }

    public DeliveryHuman(int id, String name, Address address, String email, String phoneNumber) {
        this();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    static {
        JDBCConnector.registerEntity(DeliveryHuman.class, new DeliveryHuman().toEntity());
    }
}
