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
public class ProductClass extends AbstractEntity{
    private int id;
    private Product product;
    private int stock;
    
    public ProductClass() {
        super("yk_product_class");

        addAttribute("id", (v) -> setId((Integer) v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("stock", (v) -> setStock((Integer)v), this::getStock, SqlType.INT);
    }

    public ProductClass(int id, Product product, int stock) {
        this();
        this.id = id;
        this.product = product;
        this.stock = stock;
    }

    static {
        JDBCConnector.registerEntity(ProductClass.class, new ProductClass().toEntity());
    }
}
