package de.randomerror.GUI.controller;

import de.randomerror.entity.Delivery;
import de.randomerror.entity.Warehouse;
import de.randomerror.persistence.DeliveryRepo;
import de.randomerror.util.Provided;

import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class WarehouseViewController {
    public DeliveryRepo deliveryRepo;

    public List<Delivery> getDeliveries() {
        return deliveryRepo.findAll();
    }
}
