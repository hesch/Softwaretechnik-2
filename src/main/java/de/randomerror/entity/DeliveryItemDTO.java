package de.randomerror.entity;

import de.randomerror.persistence.JDBC.*;
import lombok.Data;

/**
 * Created by henri on 08.05.17.
 */
@Data
public class DeliveryItemDTO extends AbstractEntity {
    private int number;
    private ProductDTO product;
    private int pricePerItem;

    public DeliveryItemDTO() {
        super("yk_delivery_item");

        addAttribute("number", (v) -> setNumber((Integer) v), this::getNumber, SqlType.INTEGER);
        addAttribute("pricePerItem", (v) -> setPricePerItem((Integer)v), this::getPricePerItem, SqlType.INTEGER);

    }

    public DeliveryItemDTO(int number, ProductDTO product, int pricePerItem) {
        this();
        this.number = number;
        this.product = product;
        this.pricePerItem = pricePerItem;
    }

    static {
        JDBCConnector.registerEntity(DeliveryItemDTO.class, new DeliveryItemDTO().toEntity());
    }
}
