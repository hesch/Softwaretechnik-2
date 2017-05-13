package de.randomerror.persistence.JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by henri on 13.05.17.
 */
@Data
public class Attribute {
    private String name;
    private SqlType sqlType;
    private List<Constraint> constraints;
    private Object data;

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
