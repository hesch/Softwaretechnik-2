package de.randomerror.persistence;

import de.randomerror.entity.DeliveryHumanDTO;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class DeliveryHumanRepo {
    private List<DeliveryHumanDTO> database = new LinkedList<>();

    public List<DeliveryHumanDTO> findAll() {
        return database;
    }

    public void save(DeliveryHumanDTO d) {
        database.add(d);
    }
}
