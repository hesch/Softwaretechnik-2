package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Data
@AllArgsConstructor
public class Delivery {
    private int id;
    private String deliveryId;
    private List<DeliveryItem> items;
    private DeliveryHuman human;

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("deliveryId", SqlType.TEXT));

        Entity e = new Entity(Delivery.class, "yk_delivery", attributes);

        JDBCConnector.registerEntity(Delivery.class,e);
    }
}
