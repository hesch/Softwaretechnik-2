package de.randomerror.persistence;

import de.randomerror.entity.Role;
import de.randomerror.entity.UserDTO;
import de.randomerror.util.Provided;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 */
@Provided
public class UserRepo {

    private UserDTO[] database = {
            new UserDTO("sales", "test", Role.SALES_HUMAN)};


    public Optional<UserDTO> findByLogin(String login) {
        return Arrays.stream(database).filter(u -> u.getLogin().equals(login)).findFirst();
    }

}
