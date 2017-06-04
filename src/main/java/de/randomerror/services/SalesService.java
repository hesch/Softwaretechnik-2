package de.randomerror.services;

import de.randomerror.entity.CustomerDTO;
import de.randomerror.entity.OrderDTO;
import de.randomerror.entity.OrderItemDTO;
import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.DAO.OrderItemDAO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jan on 04.06.2017.
 */
@Provided
public class SalesService {

    public OrderItemDAO orderItemDAO;
    public OrderDAO orderDAO;
    public CustomerDAO customerDAO;
    public ProductClassDAO productClassDAO;

    public Optional<OrderDTO> getOrderById(int id) {
       return  orderDAO.findById(id);
    }

    public List<OrderDTO> getAllOrders() {
        return orderDAO.findAll();
    }

    public Optional<CustomerDTO> getCustomerById(int id) {
        return customerDAO.findById(id);
    }

    public void saveOrder(OrderDTO o) {
        orderDAO.save(o);
    }

    public void saveOrderItem(OrderItemDTO orderItem) {
         orderItemDAO.save(orderItem);
    }

    public List<ProductClassDTO> findAllProductClasses() {
        return productClassDAO.findAll();
    }

    public Optional<ProductClassDTO> findProductClassById(long id) {
        return productClassDAO.findById(id);
    }
}

