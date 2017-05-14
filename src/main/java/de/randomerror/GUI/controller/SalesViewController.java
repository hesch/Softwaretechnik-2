package de.randomerror.GUI.controller;

import de.randomerror.entity.Customer;
import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;
import de.randomerror.entity.ProductClass;
import de.randomerror.persistence.CustomerRepo;
import de.randomerror.persistence.OrderItemRepo;
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
    public OrderItemRepo orderItemRepo;

    public Order getOrderById(int id) {
        return orderRepo.findById(id).get();
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepo.findById(id);
    }

    public void saveNewOrder(int customerId, List<OrderItem> orderItems){
        Order o = new Order();
        Customer c = customerRepo.findById(customerId).get();
        o.setCustomer(c);

        orderRepo.save(o);
        o.setItems(orderItems);
        orderItems.forEach(orderItem -> orderItemRepo.save(orderItem));
    }

    public List<ProductClass> getInventory() {
        return productClassRepo.findAll();
    }

    public ProductClass getProductClassById(long id) {
        return productClassRepo.findById(id).get();
    }
}
