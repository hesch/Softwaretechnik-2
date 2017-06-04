package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class ProductClassDTO extends AbstractEntity{
    private ProductDTO product;
    private int stock;
    
    public ProductClassDTO() {
        super("yk_product_class");

        addAttribute("stock", (v) -> setStock((Integer)v), this::getStock, SqlType.INTEGER);
        addAttribute("product", (v) -> setProduct((ProductDTO) v), this::getProduct, SqlType.INTEGER, Constraint.FOREIGN_KEY);
    }

    public ProductClassDTO(ProductDTO product, int stock) {
        this();
        this.product = product;
        this.stock = stock;
    }

    public ProductClassDTO(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(ProductClassDTO.class, new ProductClassDTO().toEntity());
    }
}
