package de.randomerror.persistence;

import de.randomerror.entity.AddressDTO;
import de.randomerror.entity.CustomerDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.persistence.JDBC.JDBCConnector;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by henri on 04.06.17.
 */
public class OrderDAO {

    private static CustomerDAO iut; // implementation under test

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        iut = Injector.getInstance().getProvided(CustomerRepo.class);
    }

    @BeforeEach
    void testData() {
        //drop database to have a clean working environment
        Injector.getInstance().getProvided(JDBCConnector.class).createDatabaseScheme();

        //insert testdata
        CustomerDTO[] testData = new CustomerDTO[]{
                new CustomerDTO("peter",new AddressDTO("musterstraße", "42", "44145", "Dortmund", "NRW", "Germany"),"peter@test.com","9032487387443"),
                new CustomerDTO("paul",new AddressDTO("hauptstraße", "30", "44649", "Herne", "NRW", "Germany"),"paul@test.com","45242934732443"),

        };

        Arrays.stream(testData).forEach(iut::save);
    }

    @Test
    void findAllIsCorrectLength() {
        assertEquals(2, iut.findAll().size());
    }

    @Test
    void findOne() {
    }

    @Test
    void save() {
        iut.save( new CustomerDTO("klaus",new AddressDTO("teststraße", "6", "23423", "hamburg", "hamburg", "Germany"),"klaus@test.com","12378970345"));
        assertEquals(3, iut.findAll().size());
    }

    @Test
    void update() {
    }
}
