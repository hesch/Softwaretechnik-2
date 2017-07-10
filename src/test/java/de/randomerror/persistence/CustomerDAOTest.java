package de.randomerror.persistence;

import de.randomerror.entity.AddressDTO;
import de.randomerror.entity.CustomerDTO;
import de.randomerror.persistence.DAO.AddressDAO;
import de.randomerror.persistence.DAO.CustomerDAO;
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
public class CustomerDAOTest {

    private static CustomerDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException, URISyntaxException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(CustomerRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        CustomerDTO[] testData = new CustomerDTO[] {
                new CustomerDTO("Max Mustermann", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "max.mustermann@fh-dortmund.de", "102453463"),
                new CustomerDTO("Melina Musterfrau", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "melina.musterfrau@fh-dortmund.de", "102453463")
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveFailsOnAlreadySavedItem() {
        CustomerDTO customer = new CustomerDTO("Melina Musterrfrau", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "melina.musterfrau@fh-dortmund.de", "102453463");
        iut.save(customer);
        iut.save(customer); //TODO: this is strange

        assertEquals(customer, iut.findById(customer.getId()).get());
    }

    @Test
    void findByIdFindsSavedItem() {
        CustomerDTO customer = new CustomerDTO("Melina Musterrfrau", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "melina.musterfrau@fh-dortmund.de", "102453463");
        iut.save(customer);

        assertEquals(customer, iut.findById(customer.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        CustomerDTO customer = new CustomerDTO("Melina Musterrfrau", new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"), "melina.musterfrau@fh-dortmund.de", "102453463");
        iut.save(customer);
        assertEquals(customer, iut.findById(customer.getId()).get());

        customer.setName("NewName");
        iut.update(customer);

        assertEquals(customer.getName(), iut.findById(customer.getId()).get().getName());
    }
}
