package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 *
 */
@Data
public class UserDTO extends AbstractEntity {
    String login;
    String password;
    Role role;
    
    public UserDTO() {
        super("yk_user");

        addAttribute("login", (v) -> setLogin((String)v), this::getLogin, SqlType.TEXT);
        addAttribute("password", (v) -> setPassword((String)v), this::getPassword, SqlType.TEXT);
    }

    public UserDTO(String login, String password, Role role) {
        this();
        this.login = login;
        this.password = password;
        this.role = role;
    }

    static {
        JDBCConnector.registerEntity(UserDTO.class, new UserDTO().toEntity());
    }
}
