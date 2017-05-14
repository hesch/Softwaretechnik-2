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
public class DeliveryItem extends AbstractEntity {
    private int id;
    private int number;
    private Product product;
    private int pricePerItem;

    public DeliveryItem() {
        super("yk_delivery_item");

        addAttribute("id", (v) -> setId((Integer) v), this::getId, SqlType.INT, Constraint.NOT_NULL, Constraint.PRIMARY_KEY);
        addAttribute("number", (v) -> setNumber((Integer) v), this::getNumber, SqlType.INT);
        addAttribute("pricePerItem", (v) -> setPricePerItem((Integer)v), this::getPricePerItem, SqlType.INT);

    }

    public DeliveryItem(int id, int number, Product product, int pricePerItem) {
        this();
        this.id = id;
        this.number = number;
        this.product = product;
        this.pricePerItem = pricePerItem;
    }

    static {
        JDBCConnector.registerEntity(DeliveryItem.class, new DeliveryItem().toEntity());
    }
}
