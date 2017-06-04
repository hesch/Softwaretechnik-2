package de.randomerror.persistence.DAO;

import de.randomerror.entity.ProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface ProductDAO {
    Optional<ProductDTO> findById(long id);
    List<ProductDTO> findAll();
    void save(ProductDTO address);
    void update(ProductDTO address);
}
