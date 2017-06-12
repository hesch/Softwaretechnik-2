package de.randomerror.GUI.model;

import de.randomerror.entity.AbstractEntity;
import de.randomerror.entity.OrderDTO;
import de.randomerror.persistence.DAO.DAO;
import de.randomerror.persistence.DAO.OrderDAO;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Created by henri on 12.06.17.
 */
public abstract class ObservableDataList<DATA extends AbstractEntity, REPO extends DAO<DATA>> extends Observable {
    public REPO repo;
    private List<DATA> data;

    public void addElement(DATA element) {
        data.add(element);
        setChanged();
        notifyObservers(new ObservableEvent(element, EventState.ADDED));
    }

    public void removeElement(DATA element) {
        data.remove(element);
        setChanged();
        notifyObservers(new ObservableEvent(element, EventState.DELETED));
    }

    public void removeElement(long id) {
        data.stream().filter(o -> o.getId() == id).findFirst().ifPresent(o -> {
            data.remove(o);
            setChanged();
            notifyObservers(new ObservableEvent(o, EventState.DELETED));
        });
    }

    public void updateElement(DATA element) {
        data.stream().filter(o -> element.getId() == o.getId()).findFirst().ifPresent(o -> {
            data.remove(o);
            data.add(element);
            setChanged();
            notifyObservers(new ObservableEvent(element, EventState.MODIFIED));
        });
    }

    public List<DATA> getData() {
        return Collections.unmodifiableList(data);
    }

    public void onInit() {
        data = repo.findAll();
    }
}
