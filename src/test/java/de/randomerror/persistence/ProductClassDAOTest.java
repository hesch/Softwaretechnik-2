package de.randomerror.persistence;

import de.randomerror.entity.AddressDTO;
import de.randomerror.entity.CustomerDTO;
import de.randomerror.entity.ProductClassDTO;
import de.randomerror.entity.ProductDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.DAO.ProductClassDAO;
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
public class ProductClassDAOTest {
    private static ProductClassDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException, URISyntaxException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(ProductClassRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        ProductClassDTO[] testData = new ProductClassDTO[] {
                new ProductClassDTO(new ProductDTO("product1", "it's awesome", 3), 134),
                new ProductClassDTO(new ProductDTO("product2", "it's awesome 2", 4), 123)
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveFailsOnAlreadySavedItem() {
        ProductClassDTO productclass = new ProductClassDTO(new ProductDTO("product1", "it's awesome", 3), 134);
        iut.save(productclass);
        iut.save(productclass); //TODO: this is strange

        assertEquals(productclass, iut.findById(productclass.getId()).get());
    }

    @Test
    void findByIdFindsSavedItem() {
        ProductClassDTO productclass = new ProductClassDTO(new ProductDTO("product1", "it's awesome", 3), 134);
        iut.save(productclass);

        assertEquals(productclass, iut.findById(productclass.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        ProductClassDTO productclass = new ProductClassDTO(new ProductDTO("product1", "it's awesome", 3), 134);
        iut.save(productclass);
        assertEquals(productclass, iut.findById(productclass.getId()).get());

        productclass.setStock(654);
        iut.update(productclass);

        assertEquals(productclass.getStock(), iut.findById(productclass.getId()).get().getStock());
    }
}
