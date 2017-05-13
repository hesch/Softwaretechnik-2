package de.randomerror.persistence;

import de.randomerror.entity.Order;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henri on 09.05.17.
 */
@Provided
public class OrderRepo {

    private static List<Order> database = new LinkedList<>();

    public List<Order> findAll() {
        return database;
    }

    public void save(Order d) {
        database.add(d);
    }

    public static Order findById(long id) {
        return database.stream().filter(i->i.getOrderId()==id).findFirst().get();
    }
}
