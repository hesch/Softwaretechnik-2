package de.randomerror.persistence;

import de.randomerror.entity.OrderItemDTO;
import de.randomerror.entity.ProductDTO;
import de.randomerror.persistence.DAO.OrderItemDAO;
import de.randomerror.persistence.DAO.ProductDAO;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 07.06.17.
 */
public class OrderItemDAOTest {
    private static OrderItemDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException, URISyntaxException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(OrderItemRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        OrderItemDTO[] testData = new OrderItemDTO[] {
                new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 3)),
                new OrderItemDTO(8, new ProductDTO("product2", "it's awesome 2", 4))
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveFailsOnAlreadySavedItem() {
        OrderItemDTO orderItem = new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 3));
        iut.save(orderItem);
        iut.save(orderItem); //TODO: this is strange

        assertEquals(orderItem, iut.findById(orderItem.getId()).get());
    }

    @Test
    void findByIdFindsSavedItem() {
        OrderItemDTO orderItem = new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 3));
        iut.save(orderItem);

        assertEquals(orderItem, iut.findById(orderItem.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        OrderItemDTO orderItem = new OrderItemDTO(5, new ProductDTO("product1", "it's awesome", 3));
        iut.save(orderItem);
        assertEquals(orderItem, iut.findById(orderItem.getId()).get());

        orderItem.setNumber(14);
        iut.update(orderItem);

        assertEquals(orderItem.getNumber(), iut.findById(orderItem.getId()).get().getNumber());
    }

}
