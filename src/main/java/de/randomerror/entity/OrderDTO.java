package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class OrderDTO extends AbstractEntity {
    private List<OrderItemDTO> items;

    private CustomerDTO customer;

    public double getTotal(){
        return items.stream().mapToDouble(OrderItemDTO::getTotal).sum();
    }

    public void setItems(List<OrderItemDTO> orderItems) {
        orderItems.forEach(orderItem -> orderItem.setOrderId(this.getId()));
        this.items = orderItems;
    }

    public OrderDTO(List<OrderItemDTO> items, CustomerDTO customer) {
        this();
        this.items = items;
        this.customer = customer;
    }

    public OrderDTO() {

        super("yk_order");

        addAttribute("customer", (v) -> setCustomer((CustomerDTO) v), this::getCustomer, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public OrderDTO(Entity entity) {
        this();

        fromEntity(entity);
    }

    static {
        JDBCConnector.registerEntity(OrderDTO.class, new OrderDTO().toEntity());
    }
}
