package de.randomerror.persistence.JDBC;

import de.randomerror.util.Provided;
import lombok.Getter;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by henri on 10.05.17.
 */
@Provided
public class JDBCConnector {

    private static Map<Class, Entity> entities = new HashMap<>();

    @Getter
    private Connection connection;

    public JDBCConnector() {
        connect();
        createDatabaseScheme();
    }

    /**
     * Connects to the database with the data provided from the configuration class {@link Config}
     */
    public void connect() {
        try {
            // initiate the connection with the database
            connection = DriverManager.getConnection(Config.URL + Config.DATABASE, Config.USER, Config.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerEntity(Class c, Entity e) {
        entities.put(c, e);
    }
    public static Entity getEntity(Class c) {
        return entities.get(c);
    }

    public void createDatabaseScheme() {
        entities.values().forEach(entity -> {
            try {
                Statement s = connection.createStatement();

                s.addBatch("DROP TABLE IF EXISTS " + entity.getName() + ";");


                String createSql = "CREATE TABLE " + entity.getName();

                String col = entity.getAttributes().stream()
                        .map(attribute -> attribute.getName() + " "  + attribute.getSqlType() + " " + attribute.getConstraintString())
                        .collect(Collectors.joining(", "));

                createSql += "(" + col + ");";

                System.out.println("SQL: " + createSql);

                s.addBatch(createSql);

                s.executeBatch();
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateEntity(Entity entity, long id) {
        try {
            String sql = "UPDATE " + entity.getName() + " SET ";
            sql += entity.getAttributes().stream()
                    .map(attribute -> attribute.getName() + "=?")
                    .collect(Collectors.joining(", "));
            sql += "WHERE id=?;";

            PreparedStatement s = connection.prepareStatement(sql);

            IntStream.range(0, entity.getAttributes().size()).forEach(index -> {
                try {
                Attribute attribute = entity.getAttributes().get(index);
                switch(attribute.getSqlType()) {
                    case TEXT:
                        s.setString(index + 1, (String) attribute.getData());
                        break;
                    case INT:
                        s.setInt(index + 1, (Integer)attribute.getData());
                        break;
                    case REAL:
                        s.setFloat(index + 1, (Integer)attribute.getData());
                        break;
                    case BLOB:
                        System.out.println("not implemented!");
                        break;
                    default:
                        System.out.println("Type Not Found!");
                        return;
                }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            s.setLong(entity.getAttributes().size() + 1, id);

            s.execute();
            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEntity(Entity entity) {
        try {
            String sql = "INSERT INTO " + entity.getName() + " (";
            sql += entity.getAttributes().stream().map(Attribute::getName).collect(Collectors.joining(", "));
            sql += ") VALUES (";
            sql += entity.getAttributes().stream()
                    .map(attribute -> "?")
                    .collect(Collectors.joining(", "));
            sql += ");";

            PreparedStatement s = connection.prepareStatement(sql);

            IntStream.range(0, entity.getAttributes().size()).forEach(index -> {
                try {
                    Attribute attribute = entity.getAttributes().get(index);
                    switch(attribute.getSqlType()) {
                        case TEXT:
                            s.setString(index + 1, (String) attribute.getData());
                            break;
                        case INT:
                            s.setInt(index + 1, (Integer)attribute.getData());
                            break;
                        case REAL:
                            s.setFloat(index + 1, (Float) attribute.getData());
                            break;
                        case BLOB:
                            System.out.println("not implemented!");
                            break;
                        default:
                            System.out.println("Type Not Found!");
                            return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            s.execute();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Entity loadEntity(Entity entity, int id) {
        try {
            String sql = "SELECT * FROM " + entity.getName() + " WHERE id = ?;";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet result = s.executeQuery();

            IntStream.range(0, entity.getAttributes().size()).forEach(index -> {
                try {
                Attribute attribute = entity.getAttributes().get(index);
                switch(attribute.getSqlType()) {
                    case TEXT:
                        attribute.setData(result.getString(index+1));
                        break;
                    case INT:
                        attribute.setData(result.getInt(index+1));
                        break;
                    case REAL:
                        attribute.setData(result.getFloat(index+1));
                        break;
                    case BLOB:
                        System.out.println("not implemented!");
                        break;
                    default:
                        System.out.println("Type Not Found!");
                        return;
                }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
