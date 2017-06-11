package de.randomerror.persistence.DAO;

import de.randomerror.entity.AddressDTO;
import de.randomerror.entity.AddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 04.06.17.
 */
public interface AddressDAO {
    Optional<AddressDTO> findById(long id);
    List<AddressDTO> findAll();
    void save(AddressDTO address);
    void update(AddressDTO address);
}