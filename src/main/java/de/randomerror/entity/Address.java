package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.w3c.dom.Attr;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henri on 02.05.17.
 */
@Data
@AllArgsConstructor
public class Address {
    private int id;
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    public String toString() {
        return street + " " + number + ", " + zipCode + " " + city;
    }

    public Address(Entity entity) {
        this(
                (Integer) entity.getMatchingAttribute("id").getData(),
                (String) entity.getMatchingAttribute("street").getData(),
                (String) entity.getMatchingAttribute("number").getData(),
                (String) entity.getMatchingAttribute("zipCode").getData(),
                (String) entity.getMatchingAttribute("city").getData(),
                (String) entity.getMatchingAttribute("state").getData(),
                (String) entity.getMatchingAttribute("country").getData()
        );
    }

    public Entity toEntity() {
        List<Attribute> attributes = new LinkedList<>();

        Attribute a = new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        a.setData(id);
        attributes.add(a);
        a = new Attribute("street", SqlType.TEXT);
        a.setData(street);
        attributes.add(a);
        a = new Attribute("number", SqlType.TEXT);
        a.setData(number);
        attributes.add(a);
        a = new Attribute("zipCode", SqlType.TEXT);
        a.setData(zipCode);
        attributes.add(a);
        a = new Attribute("city", SqlType.TEXT);
        a.setData(city);
        attributes.add(a);
        a = new Attribute("province", SqlType.TEXT);
        a.setData(state);
        attributes.add(a);
        a = new Attribute("country", SqlType.TEXT);
        a.setData(country);
        attributes.add(a);

        return new Entity(Address.class, "address", attributes);
    }

    static {
        List<Attribute> attributes = new LinkedList<>();

        attributes.add(new Attribute("id", SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY));
        attributes.add(new Attribute("street", SqlType.TEXT));
        attributes.add(new Attribute("number", SqlType.TEXT));
        attributes.add(new Attribute("company", SqlType.TEXT));
        attributes.add(new Attribute("zipCode", SqlType.TEXT));
        attributes.add(new Attribute("city", SqlType.TEXT));
        attributes.add(new Attribute("province", SqlType.TEXT));
        attributes.add(new Attribute("country", SqlType.TEXT));

        Entity e = new Entity(Address.class, "yk_address", attributes);

        JDBCConnector.registerEntity(Address.class, e);
    }
}
