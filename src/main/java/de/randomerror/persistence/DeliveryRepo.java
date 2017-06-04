package de.randomerror.persistence;

import de.randomerror.entity.DeliveryDTO;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class DeliveryRepo {

    private List<DeliveryDTO> database = new LinkedList<>();

    public List<DeliveryDTO> findAll() {
        return database;
    }

    public void save(DeliveryDTO d) {
        database.add(d);
    }
}
