package de.randomerror.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by henri on 08.05.17.
 */
@Data
@AllArgsConstructor
public class User {
    String login;
    String password;
    Role role;
}
