package de.randomerror.GUI.controller;

import de.randomerror.GUI.model.ObservableCustomerList;
import de.randomerror.GUI.model.ObservableEvent;
import de.randomerror.GUI.model.ObservableOrderList;
import de.randomerror.GUI.model.ObservableProductClassList;
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
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *Controller for the SalesView
 */
@Provided
@Data
public class SalesViewController implements Observer {


    public ObservableOrderList orderList;
    public ObservableCustomerList customerList;
    public ObservableProductClassList productClassList;

    /**
     * Filters the observableOrderList for an order with the given id and returns the Order.
     * @param id
     * @return OrderDTO
     */
    public OrderDTO getOrderById(long id) {

        return orderList.getData().stream().filter(e -> e.getId() == id).findFirst().get();
    }

    /**
     * @return List<OrderDTO> List of all Orders contained in the OberservableOrderList.
     */
    public List<OrderDTO> getAllOrders() {
        return orderList.getData();
    }

    /**
     * Filters the observableCustomerList for a Customer with the given id and returns the Customer contained in an Optional.
     * @param id
     * @return Optional<CustomerDTO>
     */
    public Optional<CustomerDTO> getCustomerById(int id) {
        return customerList.getData().stream().filter(e -> e.getId() == id).findFirst();
    }

    /**
     * Creates a new OrderDTO with the given Arguments and adds it to the ObservableOrderList.
     * @param customerId
     * @param orderItems
     */
    public void saveNewOrder(int customerId, List<OrderItemDTO> orderItems) {
        OrderDTO o = new OrderDTO();
        CustomerDTO c = getCustomerById(customerId).get();
        o.setCustomer(c);
        o.setItems(orderItems);

        orderList.addElement(o);
    }

    /**
     *
     * @return List<ProductClassDTO> a List containign all ProductClasses.
     */
    public List<ProductClassDTO> getInventory() {
        return getProductClassList().getData();
    }

    /**
     * Filters the ObservableProductClassList for a ProductClassDTO with the given id and returns the ProductClassDTO.
     * @param id
     * @return
     */
    public ProductClassDTO getProductClassById(long id){return productClassList.getData().stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public void update(Observable o, Object arg) {
        ObservableEvent e = (ObservableEvent) arg;
        switch (e.getState()) {
            case ADDED:
            case MODIFIED:
            case DELETED://orderDAO delete;
        }

    }

    /**
     * registers the SalesviewController as Observer on the obersavable Lists
     */
    public void onInit() {
        Stream.of(customerList, orderList, productClassList).forEach(l -> l.addObserver(this));
    }
}
