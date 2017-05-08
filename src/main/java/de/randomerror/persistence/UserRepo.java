package de.randomerror.persistence;

import de.randomerror.entity.User;
import de.randomerror.util.Provided;

import java.util.Optional;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class UserRepo {

    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }

}
