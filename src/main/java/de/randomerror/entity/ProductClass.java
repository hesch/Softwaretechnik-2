package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class ProductClass extends AbstractEntity{
    private Product product;
    private int stock;
    
    public ProductClass() {
        super("yk_product_class");

        addAttribute("stock", (v) -> setStock((Integer)v), this::getStock, SqlType.INTEGER);
        addAttribute("product", (v) -> setProduct((Product) v), this::getProduct, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public ProductClass(Product product, int stock) {
        this();
        this.product = product;
        this.stock = stock;
    }

    public ProductClass(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(ProductClass.class, new ProductClass().toEntity());
    }
}
