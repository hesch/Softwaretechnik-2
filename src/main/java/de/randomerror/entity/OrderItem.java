package de.randomerror.entity;

import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class OrderItem {
    private int number;
    private Product product;
}
