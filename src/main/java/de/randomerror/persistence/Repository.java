package de.randomerror.persistence;

import de.randomerror.entity.AbstractEntity;
import de.randomerror.persistence.JDBC.Attribute;
import de.randomerror.persistence.JDBC.Entity;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by henri on 14.05.17.
 */
public abstract class Repository<T extends AbstractEntity> {

    protected Map<String, Repository> mappedAttributes = new HashMap<>();

    protected void mapToId(Attribute attribute)  {
        AbstractEntity entity = (AbstractEntity) attribute.getData();
        attribute.setData(entity.getId());
    }

    protected void mapToObject(Attribute attribute, Repository repo) {
        int id = (Integer)attribute.getData();
        Object c = repo.findById(id).get();

        attribute.setData(c);
    }

    protected Entity objectEntityToDbEntity(Entity e) {
        mappedAttributes.keySet().forEach(name -> {
            Attribute a = e.getMatchingAttribute(name);
            mappedAttributes.get(name).saveOrUpdate((AbstractEntity) a.getData());
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

    public abstract Optional<T> findById(long id);
    public abstract void save(T object);
    public abstract void update(T object);

    public void saveOrUpdate(T object) {
        if(findById(object.getId()).isPresent())
            update(object);
        else
            save(object);
    }
}
