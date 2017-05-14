package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by Henri on 02.05.17.
 */
@Data
public class Product extends AbstractEntity {
    private String name;
    private String description;
    private int price;

    public double getDoublePrice(){
        return price/100;
    }

    public Product() {
        super("yk_product");

        addAttribute("name", (v) -> setName((String)v), this::getName, SqlType.TEXT);
        addAttribute("description", (v) -> setDescription((String) v), this::getDescription, SqlType.TEXT);
        addAttribute("price", (v) -> setPrice((Integer) v), this::getPrice, SqlType.INTEGER);
    }

    public Product(String name, String description, int price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Entity e) {
        this();

        fromEntity(e);
    }

    static {
        JDBCConnector.registerEntity(Product.class, new Product().toEntity());
    }
}
