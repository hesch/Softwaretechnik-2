package de.randomerror.persistence;

import de.randomerror.entity.Customer;
import de.randomerror.util.Provided;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jan on 13.05.2017.
 */
@Provided
public class CustomerRepo {

    private List<Customer> database = new LinkedList<>();

    public Optional<Customer> findById(long id){
        return database.stream().filter(i->i.getCustomerId()==id).findFirst();
    }

    public void save(Customer c1) {
        database.add(c1);
    }
}
