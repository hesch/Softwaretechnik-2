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
import de.randomerror.services.SalesService;
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


    public SalesService salesService;
    public OrderDTO getOrderById(int id) {

        return salesService.getOrderById(id).get();
    }

    public List<OrderDTO> getAllOrders() {
        return salesService.getAllOrders();
    }

    public Optional<CustomerDTO> getCustomerById(int id) {
        return salesService.getCustomerById(id);
    }

    public void saveNewOrder(int customerId, List<OrderItemDTO> orderItems){
        OrderDTO o = new OrderDTO();
        CustomerDTO c = salesService.getCustomerById(customerId).get();
        o.setCustomer(c);
        o.setItems(orderItems);

        salesService.saveOrder(o);
    }

    public List<ProductClassDTO> getInventory() {
        return salesService.findAllProductClasses();
    }

    public ProductClassDTO getProductClassById(long id) {
        return salesService.findProductClassById(id).get();
    }
}
