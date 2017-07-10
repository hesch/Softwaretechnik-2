package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 *
 */
@Data
public class OrderItemDTO extends AbstractEntity {
    private int number;
    private ProductDTO product;
    private long orderId;

    public double getTotal(){
        return number*product.getPrice()/100;
    }
    
    public OrderItemDTO() {
        super("yk_order_item");

        addAttribute("number", (v) -> setNumber((Integer)v), this::getNumber, SqlType.INTEGER);
        addAttribute("product", (v) -> setProduct((ProductDTO) v), this::getProduct, SqlType.INTEGER, Constraint.FOREIGN_KEY);
        addAttribute("order_id", (v) -> setOrderId((Integer) v), this::getOrderId, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public OrderItemDTO(int number, ProductDTO product) {
        this();
        this.number = number;
        this.product = product;
    }

    public OrderItemDTO(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(OrderItemDTO.class, new OrderItemDTO().toEntity());
    }
}
