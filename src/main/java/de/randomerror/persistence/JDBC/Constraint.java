package de.randomerror.persistence.JDBC;

/**
 *
 */
public enum Constraint {
    NOT_NULL("NOT NULL"),
    PRIMARY_KEY("PRIMARY KEY"),
    FOREIGN_KEY("FOREIGN_KEY"),
    AUTOINCREMENT("AUTOINCREMENT");

    private String name;

    private Constraint(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
