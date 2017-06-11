package de.randomerror.persistence;

import de.randomerror.entity.ProductClassDTO;
import de.randomerror.entity.ProductDTO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.persistence.DAO.ProductDAO;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 07.06.17.
 */
public class ProductDAOTest {
    private static ProductDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(ProductRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        ProductDTO[] testData = new ProductDTO[] {
                new ProductDTO("product1", "it's awesome", 3),
                new ProductDTO("product2", "it's awesome 2", 4)
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveFailsOnAlreadySavedItem() {
        ProductDTO product1 = new ProductDTO("product1", "it's awesome", 3);
        iut.save(product1);
        iut.save(product1); //TODO: this is strange

        assertEquals(product1, iut.findById(product1.getId()).get());
    }

    @Test
    void findByIdFindsSavedItem() {
        ProductDTO product1 = new ProductDTO("product1", "it's awesome", 3);
        iut.save(product1);

        assertEquals(product1, iut.findById(product1.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        ProductDTO productclass = new ProductDTO("product1", "it's awesome", 34);
        iut.save(productclass);
        assertEquals(productclass, iut.findById(productclass.getId()).get());

        productclass.setName("stuff");
        iut.update(productclass);

        assertEquals(productclass.getName(), iut.findById(productclass.getId()).get().getName());
    }
}
