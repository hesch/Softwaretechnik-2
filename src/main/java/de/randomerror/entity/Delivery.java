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
    private int id;
    private String deliveryId;
    private List<DeliveryItem> items;
    private DeliveryHuman human;

    public Delivery(int id, String deliveryId, List<DeliveryItem> items, DeliveryHuman human) {
        this();
        this.id = id;
        this.deliveryId = deliveryId;
        this.items = items;
        this.human = human;
    }

    public Delivery() {
        super("vk_delivery");

        addAttribute("id", (v) -> setId((Integer)v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("deliveryId", (v) -> setDeliveryId((String)v), this::getDeliveryId, SqlType.TEXT);
    }

    static {
        JDBCConnector.registerEntity(Delivery.class, new Delivery().toEntity());
    }
}
