package de.randomerror.services;

import de.randomerror.entity.Role;
import de.randomerror.persistence.AddressRepo;
import de.randomerror.util.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 04.06.17.
 */
public class LoginServiceTest {
    static LoginService ls;

    @BeforeAll
    static void setup() throws IOException {
        //wire all the dependencies together
        Injector.getInstance().init();
        ls = Injector.getInstance().getProvided(LoginService.class);
    }

    @Test
    void login() {
        ls.login("sales","test");
        assertEquals(Role.SALES_HUMAN,ls.getCurrentUser().getRole());
    }
}

