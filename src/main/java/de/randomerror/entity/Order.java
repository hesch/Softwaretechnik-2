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
    private List<OrderItem> items;

    private Customer customer;

    public double getTotal(){
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    public Order(int id, List<OrderItem> items, Customer customer) {
        this();
        setId(id);
        this.items = items;
        this.customer = customer;
    }

    public Order() {

        super("yk_order");

        addAttribute("customer", (v) -> setCustomer((Customer) v), this::getCustomer, SqlType.INT, Constraint.FOREIGN_KEY);
    }

    public Order(Entity entity) {
        this();

        fromEntity(entity);
    }

    static {
        JDBCConnector.registerEntity(Order.class, new Order().toEntity());
    }
}
