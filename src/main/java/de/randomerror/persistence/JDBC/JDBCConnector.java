package de.randomerror.persistence.JDBC;

import de.randomerror.util.Provided;
import lombok.Getter;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by henri on 10.05.17.
 */
@Provided
public class JDBCConnector {

    @Getter
    private Connection connection;

    public JDBCConnector() {
        connect();
    }

    /**
     * Connects to the database with the data provided from the configuration class {@link Config}
     */
    public void connect() {
//        try {
//            // initiate the connection with the database
//            connection = DriverManager.getConnection(Config.URL + Config.DATABASE, Config.USER, Config.PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
