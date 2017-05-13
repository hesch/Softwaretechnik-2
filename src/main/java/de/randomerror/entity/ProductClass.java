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
public class ProductClass {
    private int id;
    private Product product;
    private int stock;

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("stock", SqlType.INT));

        Entity e = new Entity(ProductClass.class, "yk_product_class", attributes);

        JDBCConnector.registerEntity(ProductClass.class, e);
    }
}
