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
        addAttribute("price", (v) -> setPrice((Integer) v), this::getPrice, SqlType.INT);
    }

    public Product(int id, String name, String description, int price) {
        this();
        setId(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    static {
        JDBCConnector.registerEntity(Product.class, new Product().toEntity());
    }
}
