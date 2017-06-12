package de.randomerror.services;

import de.randomerror.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by henri on 04.06.17.
 */
public class LoginServiceTest {
    LoginService ls;
    @Test
    void login() {
        ls.login("sales","test");
        assertEquals(Role.SALES_HUMAN,ls.getCurrentUser().getRole());
    }
}

