package de.randomerror.persistence.DAO;

import de.randomerror.entity.Product;
import de.randomerror.entity.ProductClass;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface ProductDAO {
    Optional<Product> findById(long id);
    List<Product> findAll();
    void save(Product address);
    void update(Product address);
}
