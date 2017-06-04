package de.randomerror.persistence.DAO;

import de.randomerror.entity.CustomerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface CustomerDAO {
    Optional<CustomerDTO> findById(long id);
    List<CustomerDTO> findAll();
    void save(CustomerDTO address);
    void update(CustomerDTO address);
}
