package de.randomerror.services;

import de.randomerror.entity.*;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;


/**
 * Created by henri on 04.06.17.
 */

public class SalesServiceTest {
    private static SalesService salesService;
    private static OrderDAO orderMock;
    private static CustomerDAO customerMock;
    private static ProductClassDAO productClassMock;

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        salesService = Injector.getInstance().getProvided(SalesService.class);
        orderMock = Mockito.spy(salesService.orderDAO);
        salesService.orderDAO = orderMock;
        customerMock = Mockito.spy(salesService.customerDAO);
        salesService.customerDAO = customerMock;
        productClassMock = Mockito.spy(salesService.productClassDAO);
        salesService.productClassDAO = productClassMock;

    }

    @Test
    void getOrderById() {
        salesService.getOrderById(1);
        Mockito.verify(orderMock).findById(1);
    }

    @Test
    void getAllOrders() {
        salesService.getAllOrders();
        Mockito.verify(orderMock).findAll();
    }

    @Test
    void getCustomerById() {
        salesService.getCustomerById(1);
        Mockito.verify(customerMock).findById(1);
    }

    @Test
    void saveOrder() {
        OrderDTO o= new OrderDTO(Collections.singletonList(new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"));
        salesService.saveOrder(o);
        Mockito.verify(orderMock).save(o);
    }

    @Test
    void findAllProductClasses() {
        salesService.findAllProductClasses();
        Mockito.verify(productClassMock).findAll();
    }

    @Test
    void findProductClassById() {
        salesService.findProductClassById(1);
        Mockito.verify(productClassMock).findById(1);
    }


}
