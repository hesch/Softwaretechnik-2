package de.randomerror.persistence;

import de.randomerror.entity.Address;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by henri on 13.05.17.
 */
@Provided
public class AddressRepo extends Repository<Address> {
    public JDBCConnector connector;
    private Entity entity;

    public AddressRepo() {
        entity = JDBCConnector.getEntity(Address.class);
    }

    public Optional<Address> findById(int id) {
        return connector.loadEntity(entity, id).map(Address::new);
    }

    public List<Address> findAll() {
        return connector.loadAllEntities(entity).stream().map(Address::new).collect(Collectors.toList());
    }

    public void save(Address address) {
        connector.insertEntity(address.toEntity());
    }

    public void update(Address address) {connector.updateEntity(address.toEntity(), address.getId());}
}
