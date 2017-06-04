package de.randomerror.persistence;

import de.randomerror.entity.OrderItemDTO;
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
public class OrderItemRepo extends Repository<OrderItemDTO> {

    public ProductRepo productRepo;

    public JDBCConnector connector;
    private Entity entity;

    public OrderItemRepo() {
        entity = JDBCConnector.getEntity(OrderItemDTO.class);
    }

    public Optional<OrderItemDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(OrderItemDTO::new);
    }

    public List<OrderItemDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(OrderItemDTO::new).collect(Collectors.toList());
    }

    public void save(OrderItemDTO order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(OrderItemDTO object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("product", productRepo);
    }
}
