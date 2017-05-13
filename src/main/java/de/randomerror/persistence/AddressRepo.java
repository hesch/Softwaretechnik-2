package de.randomerror.persistence;

import de.randomerror.entity.Address;
import de.randomerror.entity.Order;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Provided;

import java.util.List;

/**
 * Created by henri on 13.05.17.
 */
@Provided
public class AddressRepo {
    public JDBCConnector connector;
    private Entity entity;

    public AddressRepo() {
        entity = JDBCConnector.getEntity(Address.class);
    }

    public Address findById(long id) {
        return new Address(connector.loadEntity(entity, 0));
    }

    public void save(Address address) {
        connector.insertEntity(address.toEntity());
    }
}
