package de.randomerror.persistence;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.Product;
import de.randomerror.entity.ProductClass;
import de.randomerror.persistence.DAO.ProductClassDAO;
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
public class ProductClassRepo extends Repository<ProductClass> implements ProductClassDAO {
    public ProductRepo productRepo;

    public JDBCConnector connector;
    private Entity entity;

    public ProductClassRepo() {
        entity = JDBCConnector.getEntity(ProductClass.class);
    }

    public Optional<ProductClass> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(ProductClass::new);
    }

    public List<ProductClass> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(ProductClass::new).collect(Collectors.toList());
    }

    public void save(ProductClass order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(ProductClass object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("product", productRepo);
    }
}
