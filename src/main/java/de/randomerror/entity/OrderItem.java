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
public class OrderItem extends AbstractEntity {
    private int id;
    private int number;
    private Product product;

    public double getTotal(){
        return number*product.getPrice()/100;
    }
    
    public OrderItem() {
        super("yk_order_item");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("number", (v) -> setNumber((Integer)v), this::getNumber, SqlType.INT);
    }

    public OrderItem(int id, int number, Product product) {
        this();
        this.id = id;
        this.number = number;
        this.product = product;
    }

    static {
        JDBCConnector.registerEntity(OrderItem.class, new OrderItem().toEntity());
    }
}
