package de.randomerror.persistence;

import de.randomerror.entity.*;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 07.06.17.
 */
public class OrderDAOTest {
    private static OrderDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(OrderRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        OrderDTO[] testData = new OrderDTO[] {
                new OrderDTO(Collections.singletonList(new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463")),
                new OrderDTO(Collections.singletonList(new OrderItemDTO(6, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"))
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveFailsOnAlreadySavedItem() {
        OrderDTO order = new OrderDTO(Collections.singletonList(new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"));
        iut.save(order);
        iut.save(order); //TODO: this is strange

        assertEquals(order, iut.findById(order.getId()).get());
    }

    @Test
    void findByIdFindsSavedItem() {
        OrderDTO order = new OrderDTO(Collections.singletonList(new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"));
        iut.save(order);

        assertEquals(order, iut.findById(order.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        OrderDTO order = new OrderDTO(Collections.singletonList(new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 123))), new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"));
        iut.save(order);
        assertEquals(order, iut.findById(order.getId()).get());

        order.setCustomer(new CustomerDTO("Max2 Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"));
        iut.update(order);

        assertEquals(order.getCustomer(), iut.findById(order.getId()).get().getCustomer());
    }
}
