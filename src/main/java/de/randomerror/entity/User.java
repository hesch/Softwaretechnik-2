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
    int id;
    String login;
    String password;
    Role role;
    
    public User() {
        super("yk_user");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("login", (v) -> setLogin((String)v), this::getLogin, SqlType.TEXT);
        addAttribute("password", (v) -> setPassword((String)v), this::getPassword, SqlType.TEXT);
    }

    public User(int id, String login, String password, Role role) {
        this();
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    static {
        JDBCConnector.registerEntity(User.class, new User().toEntity());
    }
}
