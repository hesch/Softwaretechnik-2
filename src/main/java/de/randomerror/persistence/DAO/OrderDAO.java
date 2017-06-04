package de.randomerror.persistence.DAO;

import de.randomerror.entity.OrderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface OrderDAO {
    Optional<OrderDTO> findById(long id);
    List<OrderDTO> findAll();
    void save(OrderDTO address);
    void update(OrderDTO address);
}
