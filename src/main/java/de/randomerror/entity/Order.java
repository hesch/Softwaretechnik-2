package de.randomerror.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Order {
    private long orderId;
    private Address deliveryAddress;
    private Address invoiceAddress;

    private List<OrderItem> items;
}
