package de.randomerror.GUI.controller;

import de.randomerror.entity.CustomerDTO;
import de.randomerror.entity.OrderDTO;
import de.randomerror.entity.OrderItemDTO;
import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.CustomerRepo;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.DAO.OrderItemDAO;
import de.randomerror.persistence.DAO.ProductClassDAO;
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
    public OrderDAO orderRepo;
    public CustomerDAO customerRepo;
    public ProductClassDAO productClassRepo;
    public OrderItemDAO orderItemRepo;

    public OrderDTO getOrderById(int id) {
        return orderRepo.findById(id).get();
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<CustomerDTO> getCustomerById(int id) {
        return customerRepo.findById(id);
    }

    public void saveNewOrder(int customerId, List<OrderItemDTO> orderItems){
        OrderDTO o = new OrderDTO();
        CustomerDTO c = customerRepo.findById(customerId).get();
        o.setCustomer(c);

        orderRepo.save(o);
        o.setItems(orderItems);
        orderItems.forEach(orderItem -> orderItemRepo.save(orderItem));
    }

    public List<ProductClassDTO> getInventory() {
        return productClassRepo.findAll();
    }

    public ProductClassDTO getProductClassById(long id) {
        return productClassRepo.findById(id).get();
    }
}
