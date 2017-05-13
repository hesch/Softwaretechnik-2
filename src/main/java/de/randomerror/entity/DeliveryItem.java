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
public class DeliveryItem {
    private int id;
    private int number;
    private Product product;
    private int pricePerItem;

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("number", SqlType.INT));
        attributes.add(new Attribute("pricePerItem", SqlType.INT));

        Entity e = new Entity(DeliveryItem.class, "yk_delivery_item", attributes);

        JDBCConnector.registerEntity(DeliveryItem.class, e);
    }
}
