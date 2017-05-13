package de.randomerror.GUI.view;

import de.randomerror.GUI.controller.WarehouseViewController;
import de.randomerror.util.Provided;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class WarehouseView implements View {
    public WarehouseViewController controller;

    private JFrame frame = new JFrame("Lagerverwaltung Yukon");
    private DefaultTableModel deliveryModel;
    private JTabbedPane tabbedPane1;
    private JTable deliveryTable;
    private JTable orderTable;
    private JTable table4;
    private JButton speichernButton;
    private JPanel warehousePanel;

    public WarehouseView() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);



        frame.add(warehousePanel);
    }

    @Override
    public void show() {
        controller.getDeliveries().forEach((delivery) -> {
            System.out.println("adding row: " + delivery.getDeliveryId() + ":" + delivery.getItems().size()+"" + ":" + delivery.getHuman().getName());
            deliveryModel.addRow(new String[] {delivery.getDeliveryId(), delivery.getItems().size()+"", delivery.getHuman().getName()});
        });
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(true);
    }

    private void createUIComponents() {
        deliveryModel = new DefaultTableModel(new String[]{"Nummer", "Anzahl der Posten", "Lieferant"}, 0);
        deliveryTable = new JTable(deliveryModel);
    }

    public void onInit() {
        System.out.println("onInit called");

    }
}
