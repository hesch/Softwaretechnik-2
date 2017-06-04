package de.randomerror.persistence.DAO;

import de.randomerror.entity.Address;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface AddressDAO {
    Optional<Address> findById(long id);
    List<Address> findAll();
    void save(Address address);
    void update(Address address);
}
