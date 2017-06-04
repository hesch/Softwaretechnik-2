package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class DeliveryDTO extends AbstractEntity {
    private String deliveryId;
    private List<DeliveryItemDTO> items;
    private DeliveryHumanDTO human;

    public DeliveryDTO(String deliveryId, List<DeliveryItemDTO> items, DeliveryHumanDTO human) {
        this();
        this.deliveryId = deliveryId;
        this.items = items;
        this.human = human;
    }

    public DeliveryDTO() {
        super("yk_delivery");

        addAttribute("deliveryId", (v) -> setDeliveryId((String)v), this::getDeliveryId, SqlType.TEXT);
    }

    static {
        JDBCConnector.registerEntity(DeliveryDTO.class, new DeliveryDTO().toEntity());
    }
}
