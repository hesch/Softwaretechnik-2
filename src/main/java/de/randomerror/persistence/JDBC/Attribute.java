package de.randomerror.persistence.JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Data
public class Attribute {
    private String name;
    private SqlType sqlType;
    private List<Constraint> constraints;
    private Object data;

    public Attribute(Attribute attribute) {
        this.name = attribute.name;
        this.sqlType = attribute.sqlType;
        this.constraints = new LinkedList<>(attribute.constraints);
        this.data = attribute.data;
    }

    public Attribute(String name, SqlType type) {
        this.name = name;
        sqlType = type;
        constraints = Collections.emptyList();
    }

    public Attribute(String name, SqlType type, Constraint... constraints) {
        this.name = name;
        sqlType = type;
        this.constraints = Arrays.asList(constraints);
    }

    public String getConstraintString() {
        return constraints.stream().map(Enum::toString).collect(Collectors.joining(" "));
    }
}
