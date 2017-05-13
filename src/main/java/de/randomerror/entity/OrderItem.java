package de.randomerror.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
@AllArgsConstructor
public class OrderItem {
    private int number;
    private Product product;

    public double getTotal(){

        return number*product.getPrice()/100;
    }
}
