package de.randomerror.persistence;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.DeliveryHuman;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class DeliveryHumanRepo {
    private List<DeliveryHuman> database = new LinkedList<>();

    public List<DeliveryHuman> findAll() {
        return database;
    }

    public void save(DeliveryHuman d) {
        database.add(d);
    }
}
