package de.randomerror.services;

import de.randomerror.GUI.model.EventState;
import de.randomerror.GUI.model.ObservableEvent;
import de.randomerror.GUI.model.ObservableOrderList;
import de.randomerror.entity.CustomerDTO;
import de.randomerror.entity.OrderDTO;
import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.DAO.OrderItemDAO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.util.Provided;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * Created by Jan on 04.06.2017.
 */
@Provided
public class SalesService implements Observer {

    public ObservableOrderList orderList;
    public OrderDAO orderDAO;
    public CustomerDAO customerDAO;
    public ProductClassDAO productClassDAO;

    public Optional<OrderDTO> getOrderById(int id) {
        return orderDAO.findById(id);
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

    public List<ProductClassDTO> findAllProductClasses() {
        return productClassDAO.findAll();
    }

    public Optional<ProductClassDTO> findProductClassById(long id) {
        return productClassDAO.findById(id);
    }

    @Override
    public void update(Observable o, Object arg) {
        ObservableEvent e = (ObservableEvent) arg;

        switch(e.getState()){
            case ADDED: orderDAO.save((OrderDTO) e.getArgument());
            case MODIFIED: orderDAO.update((OrderDTO) e.getArgument());
            case DELETED://orderDAO delete;
        }

    }
}

