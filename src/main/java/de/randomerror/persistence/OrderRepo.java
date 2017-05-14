package de.randomerror.persistence;

import de.randomerror.entity.AbstractEntity;
import de.randomerror.entity.Address;
import de.randomerror.entity.Customer;
import de.randomerror.entity.Order;
import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Henri on 09.05.17.
 */
@Provided
public class OrderRepo extends Repository<Order> {

    public CustomerRepo customerRepo;

    public JDBCConnector connector;
    private Entity entity;

    public OrderRepo() {
        entity = JDBCConnector.getEntity(Order.class);
    }

    public Order findById(int id) {
        return new Order(dbEntityToObjectEntity(connector.loadEntity(entity, id)));
    }

    public List<Order> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(Order::new).collect(Collectors.toList());
    }

    public void save(Order order) {
        connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
    }

    public void onInit() {
        mappedAttributes.put("customer", customerRepo);
    }
}
