package de.randomerror.persistence.DAO;

import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface OrderItemDAO {
    Optional<OrderItem> findById(long id);
    List<OrderItem> findAll();
    void save(OrderItem address);
    void update(OrderItem address);
}
