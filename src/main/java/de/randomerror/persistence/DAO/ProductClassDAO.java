package de.randomerror.persistence.DAO;

import de.randomerror.entity.ProductClassDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface ProductClassDAO {
    Optional<ProductClassDTO> findById(long id);
    List<ProductClassDTO> findAll();
    void save(ProductClassDTO address);
    void update(ProductClassDTO address);
}
