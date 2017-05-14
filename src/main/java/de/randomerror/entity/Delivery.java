package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class Delivery extends AbstractEntity {
    private String deliveryId;
    private List<DeliveryItem> items;
    private DeliveryHuman human;

    public Delivery(int id, String deliveryId, List<DeliveryItem> items, DeliveryHuman human) {
        this();
        setId(id);
        this.deliveryId = deliveryId;
        this.items = items;
        this.human = human;
    }

    public Delivery() {
        super("vk_delivery");

        addAttribute("deliveryId", (v) -> setDeliveryId((String)v), this::getDeliveryId, SqlType.TEXT);
    }

    static {
        JDBCConnector.registerEntity(Delivery.class, new Delivery().toEntity());
    }
}
