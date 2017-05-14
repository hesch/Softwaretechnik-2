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
    private int number;
    private Product product;
    private int orderId;

    public double getTotal(){
        return number*product.getPrice()/100;
    }
    
    public OrderItem() {
        super("yk_order_item");

        addAttribute("number", (v) -> setNumber((Integer)v), this::getNumber, SqlType.INT);
        addAttribute("product", (v) -> setProduct((Product) v), this::getProduct, SqlType.INT, Constraint.FOREIGN_KEY);
        addAttribute("order_id", (v) -> setOrderId((Integer) v), this::getOrderId, SqlType.INT, Constraint.FOREIGN_KEY);
    }

    public OrderItem(int id, int number, Product product) {
        this();
        setId(id);
        this.number = number;
        this.product = product;
    }

    public OrderItem(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(OrderItem.class, new OrderItem().toEntity());
    }
}
