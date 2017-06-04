package de.randomerror.persistence.DAO;


import de.randomerror.entity.OrderItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface OrderItemDAO {
    Optional<OrderItemDTO> findById(long id);
    List<OrderItemDTO> findAll();
    void save(OrderItemDTO address);
    void update(OrderItemDTO address);
}
