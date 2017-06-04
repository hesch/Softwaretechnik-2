package de.randomerror.persistence;

import de.randomerror.entity.CustomerDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jan on 13.05.2017.
 */
@Provided
public class CustomerRepo extends Repository<CustomerDTO> implements CustomerDAO {
    public AddressRepo addressRepo;
    public JDBCConnector connector;
    private Entity entity;

    public CustomerRepo() {
        entity = JDBCConnector.getEntity(CustomerDTO.class);
    }

    public Optional<CustomerDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(CustomerDTO::new);
    }

    public List<CustomerDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(CustomerDTO::new).collect(Collectors.toList());
    }

    public void save(CustomerDTO customer) {

        long id = connector.insertEntity(objectEntityToDbEntity(customer.toEntity()));
        customer.setId(id);
    }

    @Override
    public void update(CustomerDTO object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("address", addressRepo);
    }
}
