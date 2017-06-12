package de.randomerror.persistence.DAO;

import java.util.List;
import java.util.Optional;

/**
 * Created by henri on 12.06.17.
 */
public interface DAO<T> {
    Optional<T> findById(long id);
    List<T> findAll();
    void save(T data);
    void update(T data);
}
