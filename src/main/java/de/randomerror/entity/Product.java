package de.randomerror.entity;

import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Product {
    private long productId;
    private String name;
    private String description;
    private int price;
}
