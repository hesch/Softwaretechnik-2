package de.randomerror.persistence;

import de.randomerror.entity.Address;
import de.randomerror.entity.Customer;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jan on 13.05.2017.
 */
@Provided
public class CustomerRepo extends Repository<Customer> {
    public AddressRepo addressRepo;
    public JDBCConnector connector;
    private Entity entity;

    public CustomerRepo() {
        entity = JDBCConnector.getEntity(Customer.class);
    }

    public Optional<Customer> findById(long id) {
        return connector.loadEntity(entity, id).map(this::dbEntityToObjectEntity).map(Customer::new);
    }

    public List<Customer> findAll() {
        return connector.loadAllEntities(entity).stream().map(this::dbEntityToObjectEntity).map(Customer::new).collect(Collectors.toList());
    }

    public void save(Customer customer) {

        long id = connector.insertEntity(objectEntityToDbEntity(customer.toEntity()));
        customer.setId(id);
    }

    @Override
    public void update(Customer object) {
        connector.updateEntity(objectEntityToDbEntity(object.toEntity()), object.getId());
    }

    public void onInit() {
        mappedAttributes.put("address", addressRepo);
    }
}
