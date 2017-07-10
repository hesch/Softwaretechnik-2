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
import java.util.stream.Stream;

/**
 *
 */
@Provided
public class SalesService implements Observer {

    public ObservableOrderList orderList;
    public OrderDAO orderDAO;
    public CustomerDAO customerDAO;
    public ProductClassDAO productClassDAO;

    /**
     *
     * @param id
     * @return
     */
    public Optional<OrderDTO> getOrderById(int id) {
        return orderDAO.findById(id);
    }

    /**
     *
     * @return
     */
    public List<OrderDTO> getAllOrders() {
        return orderDAO.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<CustomerDTO> getCustomerById(int id) {
        return customerDAO.findById(id);
    }

    /**
     *
     * @param o
     */
    public void saveOrder(OrderDTO o) {
        orderDAO.save(o);
    }

    /**
     *
     * @return
     */
    public List<ProductClassDTO> findAllProductClasses() {
        return productClassDAO.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     */
    public void onInit() {
        orderList.addObserver(this);
    }
}

