package de.randomerror.persistence;

import de.randomerror.entity.Customer;
import de.randomerror.entity.Delivery;
import de.randomerror.entity.OrderItem;
import de.randomerror.entity.Product;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class ProductRepo extends Repository<Product> {

    public JDBCConnector connector;
    private Entity entity;

    public ProductRepo() {
        entity = JDBCConnector.getEntity(Product.class);
    }

    public Optional<Product> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(Product::new);
    }

    public List<Product> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(Product::new).collect(Collectors.toList());
    }

    public void save(Product order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(Product object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }
}
