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
          new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany")
        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void firstTest() {
        assertEquals(2, 1+1);
    }


    @Test
    void findAllIsCorrectLength() {
        assertEquals(1, iut.findAll().size());
    }
}
