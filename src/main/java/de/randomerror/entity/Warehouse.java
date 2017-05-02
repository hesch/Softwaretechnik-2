package de.randomerror.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Warehouse {
    private List<ProductClass> stock;
}
