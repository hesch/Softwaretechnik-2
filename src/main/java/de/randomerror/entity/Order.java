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
public class Order {
    private int id;
    private Address deliveryAddress;
    private Address invoiceAddress;

    private List<OrderItem> items;

    private Customer customer;

    public double getTotal(){
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));

        Entity e = new Entity(Order.class, "yk_order", attributes);

        JDBCConnector.registerEntity(Order.class, e);
    }
}
