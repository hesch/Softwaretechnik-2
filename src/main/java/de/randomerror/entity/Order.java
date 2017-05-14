package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

import java.util.List;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Order extends AbstractEntity {
    private List<OrderItem> items;

    private Customer customer;

    public double getTotal(){
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    public void setItems(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> orderItem.setOrderId(this.getId()));
        this.items = orderItems;
    }

    public Order(List<OrderItem> items, Customer customer) {
        this();
        this.items = items;
        this.customer = customer;
    }

    public Order() {

        super("yk_order");

        addAttribute("customer", (v) -> setCustomer((Customer) v), this::getCustomer, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public Order(Entity entity) {
        this();

        fromEntity(entity);
    }

    static {
        JDBCConnector.registerEntity(Order.class, new Order().toEntity());
    }
}
