package de.randomerror.persistence.JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Data
@AllArgsConstructor
public class Entity {
    private Class clazz;
    private String name;
    private List<Attribute> attributes;

    public Entity(Entity e) {
        this.clazz = e.clazz;
        this.name = e.name;

        attributes = e.attributes.stream().map(Attribute::new).collect(Collectors.toList());
    }

    /**
     *
     * @param name
     * @return
     */
    public Attribute getMatchingAttribute(String name) {
        return attributes.stream().filter(a -> a.getName().equals(name)).findFirst().get();
    }
}
