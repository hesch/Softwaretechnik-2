package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 *
 */
@Data
public class ProductDTO extends AbstractEntity {
    private String name;
    private String description;
    private int price;

    public double getDoublePrice(){
        return price/100;
    }

    public ProductDTO() {
        super("yk_product");

        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("description", (v) -> setDescription((String) v), this::getDescription, SqlType.TEXT);
        addAttribute("price", (v) -> setPrice((Integer) v), this::getPrice, SqlType.INTEGER);
    }

    public ProductDTO(String name, String description, int price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(ProductDTO.class, new ProductDTO().toEntity());
    }
}
