package de.randomerror.persistence;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.ProductClass;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class ProductClassRepo {
    private List<ProductClass> database = new LinkedList<>();

    public List<ProductClass> findAll() {
        return database;
    }

    public void save(ProductClass d) {
        database.add(d);
    }

    public ProductClass findById(long id) {
        return database.stream().filter(i->i.getProduct().getProductId()==id).findFirst().get();
    }
}
