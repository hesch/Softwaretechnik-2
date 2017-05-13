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
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;

    public double getDoublePrice(){
        return price/100;
    }

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("name", SqlType.TEXT));
        attributes.add(new Attribute("description", SqlType.TEXT));
        attributes.add(new Attribute("price", SqlType.INT));

        Entity e = new Entity(Product.class, "yk_product", attributes);

        JDBCConnector.registerEntity(Product.class, e);
    }
}
