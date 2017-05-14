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
public class Order extends AbstractEntity {
    private int id;
    private Address deliveryAddress;
    private Address invoiceAddress;

    private List<OrderItem> items;

    private Customer customer;

    public double getTotal(){
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    public Order(int id, Address deliveryAddress, Address invoiceAddress, List<OrderItem> items, Customer customer) {
        this();
        this.id = id;
        this.deliveryAddress = deliveryAddress;
        this.invoiceAddress = invoiceAddress;
        this.items = items;
        this.customer = customer;
    }

    public Order() {

        super("yk_order");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);

    }

    static {
        JDBCConnector.registerEntity(Order.class, new Order().toEntity());
    }
}
