package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 *
 */
@Data
public class CustomerDTO extends AbstractEntity {
    private String name;
    private AddressDTO address;
    private String email;
    private String phoneNumber;

    public CustomerDTO() {
        super("yk_customer");

        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("email", (v) -> setEmail((String)v), this::getEmail, SqlType.TEXT);
        addAttribute("phoneNumber", (v) -> setPhoneNumber((String)v), this::getPhoneNumber, SqlType.TEXT);
        addAttribute("address", (v) -> setAddress((AddressDTO)v), this::getAddress, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public CustomerDTO(String name, AddressDTO address, String email, String phoneNumber) {
        this();
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CustomerDTO(Entity entity) {
        this();

        fromEntity(entity);
    }

    static {
        JDBCConnector.registerEntity(CustomerDTO.class, new CustomerDTO().toEntity());
    }
}
