package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class User extends AbstractEntity {
    String login;
    String password;
    Role role;
    
    public User() {
        super("yk_user");

        addAttribute("login", (v) -> setLogin((String)v), this::getLogin, SqlType.TEXT);
        addAttribute("password", (v) -> setPassword((String)v), this::getPassword, SqlType.TEXT);
    }

    public User(String login, String password, Role role) {
        this();
        this.login = login;
        this.password = password;
        this.role = role;
    }

    static {
        JDBCConnector.registerEntity(User.class, new User().toEntity());
    }
}
