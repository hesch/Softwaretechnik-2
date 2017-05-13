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
@AllArgsConstructor
public class DeliveryHuman {
    private int id;
    private String name;
    private Address address;
    private String email;
    private String phoneNumber;

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("name", SqlType.TEXT));
        attributes.add(new Attribute("email", SqlType.TEXT));
        attributes.add(new Attribute("phoneNumber", SqlType.TEXT));

        Entity e = new Entity(DeliveryHuman.class, "yk_delivery_human", attributes);

        JDBCConnector.registerEntity(DeliveryHuman.class, e);
    }
}
