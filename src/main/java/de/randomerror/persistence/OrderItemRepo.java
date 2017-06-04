package de.randomerror.persistence;

import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;
import de.randomerror.persistence.DAO.OrderItemDAO;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by henri on 14.05.17.
 */
@Provided
public class OrderItemRepo extends Repository<OrderItem> implements OrderItemDAO {

    public ProductRepo productRepo;

    public JDBCConnector connector;
    private Entity entity;

    public OrderItemRepo() {
        entity = JDBCConnector.getEntity(OrderItem.class);
    }

    public Optional<OrderItem> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(OrderItem::new);
    }

    public List<OrderItem> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(OrderItem::new).collect(Collectors.toList());
    }

    public void save(OrderItem order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(OrderItem object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("product", productRepo);
    }
}
