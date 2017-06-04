package de.randomerror.persistence;

import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class ProductClassRepo extends Repository<ProductClassDTO> implements ProductClassDAO {
    public ProductRepo productRepo;

    public JDBCConnector connector;
    private Entity entity;

    public ProductClassRepo() {
        entity = JDBCConnector.getEntity(ProductClassDTO.class);
    }

    public Optional<ProductClassDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(ProductClassDTO::new);
    }

    public List<ProductClassDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(ProductClassDTO::new).collect(Collectors.toList());
    }

    public void save(ProductClassDTO order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(ProductClassDTO object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("product", productRepo);
    }
}
