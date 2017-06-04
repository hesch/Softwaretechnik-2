package de.randomerror.GUI.controller;

import de.randomerror.entity.DeliveryDTO;
import de.randomerror.persistence.DeliveryRepo;
import de.randomerror.util.Provided;

import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class WarehouseViewController {
    public DeliveryRepo deliveryRepo;

    public List<DeliveryDTO> getDeliveries() {
        return deliveryRepo.findAll();
    }
}
