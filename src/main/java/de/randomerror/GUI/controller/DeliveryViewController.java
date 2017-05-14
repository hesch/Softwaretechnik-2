package de.randomerror.GUI.controller;

import de.randomerror.persistence.DeliveryHumanRepo;
import de.randomerror.persistence.DeliveryRepo;
import de.randomerror.persistence.ProductClassRepo;
import de.randomerror.util.Provided;

/**
 * Created by Jan on 14.05.2017.
 */
@Provided
public class DeliveryViewController {
    public DeliveryHumanRepo deliveryHumanRepo;
    public DeliveryRepo deliveryRepo;
    public ProductClassRepo productClassRepo;

}
