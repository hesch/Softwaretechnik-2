package de.randomerror.GUI.model;

import de.randomerror.entity.OrderDTO;
import de.randomerror.persistence.DAO.OrderDAO;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;
import lombok.Getter;

import java.util.*;

/**
 * Created by henri on 12.06.17.
 */
@Provided
public class ObservableOrderList extends ObservableDataList<OrderDTO, OrderDAO> {
    @Override
    public void onInit() {
        Injector i = Injector.getInstance();
        repo = i.getProvided(OrderDAO.class);
        super.onInit();
    }
}
