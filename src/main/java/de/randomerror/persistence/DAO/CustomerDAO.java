package de.randomerror.persistence.DAO;

import de.randomerror.entity.Address;
import de.randomerror.entity.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface CustomerDAO {
    Optional<Customer> findById(long id);
    List<Customer> findAll();
    void save(Customer address);
    void update(Customer address);
}
