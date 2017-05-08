package de.randomerror.entity;

import lombok.Data;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class User {
    String login;
    String password;
    Role role;
}
