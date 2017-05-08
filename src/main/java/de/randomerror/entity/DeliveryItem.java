package de.randomerror.entity;

import lombok.Data;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class DeliveryItem {
    private int number;
    private Product product;
    private int pricePerItem;
}
