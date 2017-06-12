package de.randomerror.GUI.model;

import de.randomerror.entity.OrderDTO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.util.Provided;

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

    public void onInit() {
        data = orderRepo.findAll();
    }
}
