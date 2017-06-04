package de.randomerror.persistence.DAO;

import de.randomerror.entity.Customer;
import de.randomerror.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface OrderDAO {
    Optional<Order> findById(long id);
    List<Order> findAll();
    void save(Order address);
    void update(Order address);
}
