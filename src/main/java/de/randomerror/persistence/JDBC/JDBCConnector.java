package de.randomerror.persistence.JDBC;

import de.randomerror.util.Provided;
import lombok.Getter;

import java.sql.*;
import java.util.*;
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
                    case INTEGER:
                        if(attribute.getData() instanceof Integer)
                            s.setInt(index + 1, (Integer) attribute.getData());
                        else if(attribute.getData() instanceof Long)
                            s.setLong(index+1, (Long) attribute.getData());
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

    public long insertEntity(Entity entity) {
        long id = -1;

        try {

            List<Attribute> attributesWithoutId = entity.getAttributes().stream().filter(attribute -> !attribute.getName().equals("id")).collect(Collectors.toList());

            String sql = "INSERT INTO " + entity.getName() + " (";
            sql += attributesWithoutId.stream().map(Attribute::getName).collect(Collectors.joining(", "));
            sql += ") VALUES (";
            sql += attributesWithoutId.stream()
                    .map(attribute -> "?")
                    .collect(Collectors.joining(", "));
            sql += ");";

            PreparedStatement s = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            IntStream.range(0, attributesWithoutId.size()).forEach(index -> {
                try {
                    Attribute attribute = attributesWithoutId.get(index);
                    switch(attribute.getSqlType()) {
                        case TEXT:
                            s.setString(index + 1, (String) attribute.getData());
                            break;
                        case INTEGER:
                            if(attribute.getData() instanceof Integer)
                                s.setInt(index + 1, (Integer) attribute.getData());
                            else if(attribute.getData() instanceof Long)
                                s.setLong(index+1, (Long) attribute.getData());
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
            ResultSet res = s.getGeneratedKeys();
            if (res.next())
                id = res.getLong(1);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public Optional<Entity> loadEntity(Entity entity, long id) {
        try {
            String sql = "SELECT * FROM " + entity.getName() + " WHERE id = ?;";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setLong(1, id);

            ResultSet result = s.executeQuery();

            IntStream.range(0, entity.getAttributes().size()).forEach(index -> {
                try {
                Attribute attribute = entity.getAttributes().get(index);
                switch(attribute.getSqlType()) {
                    case TEXT:
                        attribute.setData(result.getString(index+1));
                        break;
                    case INTEGER:
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
                    throw new RuntimeException(e);
                }
            });

            s.close();

        } catch (Exception e) {
            System.out.println("Entity with id: " + id + " not found in table " + entity.getName());
            return Optional.empty();
        }

        return Optional.of(entity);
    }

    public List<Entity> loadAllEntities(Entity entity) {
        List<Entity> results = null;
        try {
            results = new LinkedList<>();
            String sql = "SELECT * FROM " + entity.getName() + ";";
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery(sql);

            while(result.next()) {
                Entity current = new Entity(entity);

                IntStream.range(0, entity.getAttributes().size()).forEach(index -> {
                    try {
                        Attribute attribute = current.getAttributes().get(index);
                        switch (attribute.getSqlType()) {
                            case TEXT:
                                attribute.setData(result.getString(index + 1));
                                break;
                            case INTEGER:
                                attribute.setData(result.getInt(index + 1));
                                break;
                            case REAL:
                                attribute.setData(result.getFloat(index + 1));
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

                results.add(current);
            }

            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}