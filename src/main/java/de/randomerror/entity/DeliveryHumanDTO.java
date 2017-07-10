package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 *
 */
@Data
public class DeliveryHumanDTO extends AbstractEntity {
    private String name;
    private AddressDTO address;
    private String email;
    private String phoneNumber;
    
    public DeliveryHumanDTO() {
        super("yk_delivery_human");

        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("email", (v) -> setEmail((String)v), this::getEmail, SqlType.TEXT);
        addAttribute("phoneNumber", (v) -> setPhoneNumber((String)v), this::getPhoneNumber, SqlType.TEXT);
    }

    public DeliveryHumanDTO(String name, AddressDTO address, String email, String phoneNumber) {
        this();
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    static {
        JDBCConnector.registerEntity(DeliveryHumanDTO.class, new DeliveryHumanDTO().toEntity());
    }
}
