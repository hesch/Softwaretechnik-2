package de.randomerror.GUI.controller;

import de.randomerror.entity.Customer;
import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;
import de.randomerror.entity.ProductClass;
import de.randomerror.persistence.CustomerRepo;
import de.randomerror.persistence.OrderRepo;
import de.randomerror.persistence.ProductClassRepo;
import de.randomerror.util.Provided;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jan on 13.05.2017.
 */
@Provided
@Data
public class SalesViewController {
    public OrderRepo orderRepo;
    public CustomerRepo customerRepo;
    public ProductClassRepo productClassRepo;

    public Order getOrderById(long id) {
        return orderRepo.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Customer> getCustomerById(long id) {
        return customerRepo.findById(id);
    }

    public void saveNewOrder(long customerId, List<OrderItem> orderItems){

    }

    public List<ProductClass> getInventory() {
        return productClassRepo.findAll();
    }

    public ProductClass getProductClassById(long id) {
        return productClassRepo.findById(id);
    }
}
