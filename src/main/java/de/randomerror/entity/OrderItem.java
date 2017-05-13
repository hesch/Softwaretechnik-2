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
public class OrderItem {
    private int id;
    private int number;
    private Product product;

    public double getTotal(){
        return number*product.getPrice()/100;
    }

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("number", SqlType.INT));

        Entity e = new Entity(OrderItem.class, "yk_order_item", attributes);

        JDBCConnector.registerEntity(OrderItem.class, e);
    }
}
