package de.randomerror.GUI.model;

import de.randomerror.entity.OrderDTO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.util.Provided;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by henri on 12.06.17.
 */
@Provided
public class ObservableOrderList extends Observable {
    public OrderDAO orderRepo;
    private List<OrderDTO> data;

    public void addElement(OrderDTO order) {
        data.add(order);
        setChanged();
        notifyObservers(new ObservableEvent(order, EventState.ADDED));
    }

    public void removeElement(OrderDTO order) {
        data.remove(order);
        setChanged();
        notifyObservers(new ObservableEvent(order, EventState.DELETED));
    }

    public void removeElement(long id) {
        data.stream().filter(o -> o.getId() == id).findFirst().ifPresent(o -> {
            data.remove(o);
            setChanged();
            notifyObservers(new ObservableEvent(o, EventState.DELETED));
        });
    }

    public void updateElement(OrderDTO order) {
        data.stream().filter(o -> order.getId() == o.getId()).findFirst().ifPresent(o -> {
            data.remove(o);
            data.add(order);
            setChanged();
            notifyObservers(new ObservableEvent(order, EventState.MODIFIED));
        });
    }

    public List<OrderDTO> getData() {
        return Collections.unmodifiableList(data);
    }

    public void onInit() {
        data = orderRepo.findAll();
    }
}
