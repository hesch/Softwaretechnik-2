package de.randomerror.persistence;

import de.randomerror.entity.Role;
import de.randomerror.entity.User;
import de.randomerror.util.Provided;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class UserRepo {

    private User[] database = {
            new User("warehouse", "test", Role.WAREHOUSE_HUMAN),
            new User("sales", "test", Role.SALES_HUMAN),
            new User("supply", "test", Role.SUPPLY_HUMAN)};

    public Optional<User> findByLogin(String login) {
        return Arrays.stream(database).filter(u -> u.getLogin().equals(login)).findFirst();
    }

}
