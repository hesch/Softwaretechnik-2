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
@AllArgsConstructor
public class User {
    String login;
    String password;
    Role role;

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("stock", SqlType.INT));

        Entity e = new Entity(User.class, "yk_user", attributes);

        JDBCConnector.registerEntity(User.class, e);
    }
}
