package de.randomerror.persistence;

import de.randomerror.entity.AddressDTO;
import de.randomerror.persistence.DAO.AddressDAO;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 04.06.17.
 */
public class AddressDAOTest {

    private static AddressDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(AddressRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        AddressDTO[] testData = new AddressDTO[] {
          new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"),
          new AddressDTO("Uhlandstraße", "22", "44148", "Dortmund", "NRW", "Germany")
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void saveSavesNewItem() {
        AddressDTO address = new AddressDTO("Emil-Figge-Straße", "42", "44269", "Dortmund", "NRW", "Germany");
        iut.save(address);
        long id = address.getId();
        iut.save(address);
        long id2 = address.getId();

        assertNotEquals(id, id2);
    }

    @Test
    void findByIdFindsSavedItem() {
        AddressDTO address = new AddressDTO("Emil-Figge-Straße", "42", "44269", "Dortmund", "NRW", "Germany");
        iut.save(address);

        assertEquals(address, iut.findById(address.getId()).get());
    }

    @Test
    void findByIdReutrnsEmptyOptionalIfNotFound() {
        iut.findById(1235763457L).ifPresent(s -> {
            fail(s + " should not be present");
        });
    }

    @Test
    void updateUpdatesStuff() {
        AddressDTO address = new AddressDTO("Emil-Figge-Straße", "42", "44269", "Dortmund", "NRW", "Germany");
        iut.save(address);
        assertEquals(address, iut.findById(address.getId()).get());

        address.setNumber("43");
        iut.update(address);

        assertEquals(address.getNumber(), iut.findById(address.getId()).get().getNumber());
    }
}
