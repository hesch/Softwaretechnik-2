package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class OrderItem extends AbstractEntity {
    private int number;
    private Product product;
    private long orderId;

    public double getTotal(){
        return number*product.getPrice()/100;
    }
    
    public OrderItem() {
        super("yk_order_item");

        addAttribute("number", (v) -> setNumber((Integer)v), this::getNumber, SqlType.INTEGER);
        addAttribute("product", (v) -> setProduct((Product) v), this::getProduct, SqlType.INTEGER, Constraint.FOREIGN_KEY);
        addAttribute("order_id", (v) -> setOrderId((Integer) v), this::getOrderId, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public OrderItem(int number, Product product) {
        this();
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
