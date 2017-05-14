package de.randomerror.persistence;

import de.randomerror.entity.AbstractEntity;
import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henri on 14.05.17.
 */
public abstract class Repository<T> {

    protected Map<String, Repository> mappedAttributes = new HashMap<>();

    protected void mapToId(Attribute attribute)  {
        AbstractEntity entity = (AbstractEntity) attribute.getData();
        attribute.setData(entity.getId());
    }

    protected void mapToObject(Attribute attribute, Repository repo) {
        int id = (Integer)attribute.getData();
        Object c = repo.findById(id);

        attribute.setData(c);
    }

    protected Entity objectEntityToDbEntity(Entity e) {
        mappedAttributes.keySet().forEach(name -> {
            Attribute a = e.getMatchingAttribute(name);
            mappedAttributes.get(name).save(a.getData());
            mapToId(a);
        });
        return e;
    }

    protected Entity dbEntityToObjectEntity(Entity entity) {
        mappedAttributes.forEach((name, repo) -> {
            mapToObject(entity.getMatchingAttribute(name), repo);
        });
        return entity;
    }

    public abstract T findById(int id);
    public abstract void save(T object);
}
