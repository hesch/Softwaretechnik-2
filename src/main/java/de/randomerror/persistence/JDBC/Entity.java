package de.randomerror.persistence.JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by henri on 13.05.17.
 */
@Data
@AllArgsConstructor
public class Entity {
    private Class clazz;
    private String name;
    private List<Attribute> attributes;

    public Attribute getMatchingAttribute(String name) {
        return attributes.stream().filter(a -> a.getName().equals(name)).findFirst().get();
    }
}
