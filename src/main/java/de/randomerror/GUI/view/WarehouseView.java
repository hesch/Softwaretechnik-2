package de.randomerror.GUI.view;

import de.randomerror.entity.Warehouse;
import de.randomerror.util.Provided;

import javax.swing.*;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class WarehouseView implements View {

    private JFrame frame = new JFrame("Lagerverwaltung Yukon");

    public WarehouseView() {

    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(true);
    }
}
