package de.randomerror.GUI.model;

import de.randomerror.entity.AbstractEntity;
import de.randomerror.entity.OrderDTO;
import de.randomerror.persistence.DAO.DAO;
import de.randomerror.persistence.DAO.OrderDAO;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 *
 */
public abstract class ObservableDataList<DATA extends AbstractEntity, REPO extends DAO<DATA>> extends Observable {
    public REPO repo;
    private List<DATA> data;

    /**
     *
     * @param element
     */
    public void addElement(DATA element) {
        data.add(element);
        setChanged();
        notifyObservers(new ObservableEvent(element, EventState.ADDED));
    }

    /**
     *
     * @param element
     */
    public void removeElement(DATA element) {
        data.remove(element);
        setChanged();
        notifyObservers(new ObservableEvent(element, EventState.DELETED));
    }

    /**
     *
     * @param id
     */
    public void removeElement(long id) {
        data.stream().filter(o -> o.getId() == id).findFirst().ifPresent(o -> {
            data.remove(o);
            setChanged();
            notifyObservers(new ObservableEvent(o, EventState.DELETED));
        });
    }

    /**
     *
     * @param element
     */
    public void updateElement(DATA element) {
        data.stream().filter(o -> element.getId() == o.getId()).findFirst().ifPresent(o -> {
            data.remove(o);
            data.add(element);
            setChanged();
            notifyObservers(new ObservableEvent(element, EventState.MODIFIED));
        });
    }

    /**
     *
     * @return
     */
    public List<DATA> getData() {
        return Collections.unmodifiableList(data);
    }

    /**
     *
     */
    public void onInit() {
        data = repo.findAll();
    }
}
