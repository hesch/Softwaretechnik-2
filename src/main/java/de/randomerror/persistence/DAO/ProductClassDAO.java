package de.randomerror.persistence.DAO;

import de.randomerror.entity.OrderItem;
import de.randomerror.entity.ProductClass;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface ProductClassDAO {
    Optional<ProductClass> findById(long id);
    List<ProductClass> findAll();
    void save(ProductClass address);
    void update(ProductClass address);
}
