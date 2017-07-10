package de.randomerror.persistence;

import de.randomerror.entity.ProductDTO;
import de.randomerror.persistence.DAO.ProductDAO;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Provided
public class ProductRepo extends Repository<ProductDTO> implements ProductDAO {

    public JDBCConnector connector;
    private Entity entity;

    public ProductRepo() {
        entity = JDBCConnector.getEntity(ProductDTO.class);
    }

    public Optional<ProductDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(ProductDTO::new);
    }

    public List<ProductDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(ProductDTO::new).collect(Collectors.toList());
    }

    public void save(ProductDTO order) {
        long id = connector.insertEntity(objectEntityToDbEntity(order.toEntity()));
        order.setId(id);
    }

    @Override
    public void update(ProductDTO object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }
}
