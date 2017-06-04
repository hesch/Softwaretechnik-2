package de.randomerror.persistence;

import de.randomerror.entity.AddressDTO;
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
public class AddressRepo extends Repository<AddressDTO> {
    public JDBCConnector connector;
    private Entity entity;

    public AddressRepo() {
        entity = JDBCConnector.getEntity(AddressDTO.class);
    }

    public Optional<AddressDTO> findById(long id) {
        return connector.loadEntity(entity, id).map(AddressDTO::new);
    }

    public List<AddressDTO> findAll() {
        return connector.loadAllEntities(entity).stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    public void save(AddressDTO address) {
        long id = connector.insertEntity(address.toEntity());
        address.setId(id);
    }

    public void update(AddressDTO address) {connector.updateEntity(address.toEntity(), address.getId());}
}
