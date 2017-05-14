package de.randomerror.entity;

import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Constraint;
import de.randomerror.persistence.JDBC.Entity;
import de.randomerror.persistence.JDBC.SqlType;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by henri on 14.05.17.
 */
public abstract class AbstractEntity {

    private Map<String, Consumer> setterMap = new HashMap<>();
    private Map<String, Supplier> getterMap = new HashMap<>();
    private Map<String, Attribute> attributeMap = new HashMap<>();

    private String entityName;

    public AbstractEntity(String entityName) {
        this.entityName = entityName;
    }

    protected void addAttribute(String name, Consumer setter, Supplier getter, SqlType type, Constraint... constraints) {
        setterMap.put(name, setter);
        getterMap.put(name, getter);

        attributeMap.put(name, new Attribute(name, type, constraints));
    }


    public Entity toEntity() {
        attributeMap.forEach((name, attribute) -> {
            attribute.setData(getterMap.get(name).get());
        });

        return new Entity(Address.class, entityName, new LinkedList<>(attributeMap.values()));
    }

    public void fromEntity(Entity e) {
        e.getAttributes().forEach(attribute -> {
            setterMap.get(attribute.getName()).accept(attribute.getData());
        });
    }
}
