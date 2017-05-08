package de.randomerror.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class Delivery {
    private List<DeliveryItem> items;
    private DeliveryHuman human;
}
