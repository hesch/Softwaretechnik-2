package de.randomerror.persistence;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.Product;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class ProductRepo {
    private List<Product> database = new LinkedList<>();

    public List<Product> findAll() {
        return database;
    }

    public void save(Product d) {
        database.add(d);
    }
}
