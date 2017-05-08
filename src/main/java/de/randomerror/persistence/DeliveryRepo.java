package de.randomerror.persistence;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.DeliveryItem;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class DeliveryRepo {

    private List<Delivery> database = new LinkedList<>();

    public List<Delivery> findAll() {
        return database;
    }

    public void save(Delivery d) {
        database.add(d);
    }
}
