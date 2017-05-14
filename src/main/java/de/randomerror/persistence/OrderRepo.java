package de.randomerror.persistence;

import de.randomerror.entity.*;
import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Henri on 09.05.17.
 */
@Provided
public class OrderRepo extends Repository<Order> {

    public CustomerRepo customerRepo;
    public OrderItemRepo orderItemRepo;

    public JDBCConnector connector;
    private Entity entity;

    public OrderRepo() {
        entity = JDBCConnector.getEntity(Order.class);
    }

    public Optional<Order> findById(int id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(this::mapEntity);
    }

    public List<Order> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(this::mapEntity).collect(Collectors.toList());
    }

    private Order mapEntity(Entity entity) {
        Order order = new Order(entity);
        List<OrderItem> items = orderItemRepo.findAll()
                .stream()
                .filter(orderItem -> orderItem.getOrderId() == order.getId())
                .collect(Collectors.toList());
        order.setItems(items);
        return order;
    }

    public void save(Order order) {
        connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
    }

    @Override
    public void update(Order object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("customer", customerRepo);
    }
}
