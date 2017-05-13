package de.randomerror.persistence.JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by henri on 13.05.17.
 */
@Data
@AllArgsConstructor
public class Attribute<T> {
    private String name;
    private SqlType sqlType;
    private List<Constraint> constraints;
    private T data;

    public String getConstraintString() {
        return constraints.stream().map(Enum::toString).collect(Collectors.joining(" "));
    }
}
