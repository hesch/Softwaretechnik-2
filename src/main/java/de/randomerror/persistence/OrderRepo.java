package de.randomerror.persistence;

import de.randomerror.entity.*;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Henri on 09.05.17.
 */
@Provided
public class OrderRepo extends Repository<OrderDTO> implements OrderDAO {

    public CustomerRepo customerRepo;
    public OrderItemRepo orderItemRepo;

    public JDBCConnector connector;
    private Entity entity;

    public OrderRepo() {
        entity = JDBCConnector.getEntity(OrderDTO.class);
    }

    public Optional<OrderDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(this::mapEntity);
    }

    public List<OrderDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(this::mapEntity).collect(Collectors.toList());
    }

    private OrderDTO mapEntity(Entity entity) {
        OrderDTO order = new OrderDTO(entity);
        List<OrderItemDTO> items = orderItemRepo.findAll()
                .stream()
                .filter(orderItem -> orderItem.getOrderId() == order.getId())
                .collect(Collectors.toList());
        order.setItems(items);
        return order;
    }

    public void save(OrderDTO order) {

        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(OrderDTO object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("customer", customerRepo);
    }
}
