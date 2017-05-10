package de.randomerror.GUI.view;

import de.randomerror.persistence.OrderRepo;
import de.randomerror.util.Provided;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class SalesView implements View {
    public OrderRepo orderRepo;

    private JFrame frame = new JFrame("Yukon");

    private DefaultTableModel orderModel;

    private JPanel salespanel;
    private JTable Bestellungen;
    private JTable table2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JScrollPane BestellungenScroll;
    private JTabbedPane tabbedPane1;
    private JTextField textField5;
    private JSpinner spinner1;
    private JButton hinzufügenButton;
    private JTable table1;
    private JTextField textField6;
    private JTextField textField8;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTable table3;
    private JTextField textField14;
    private JButton abbrechenButton;
    private JButton speichernButton;

    public SalesView() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,500);

        frame.add(salespanel);
    }

    @Override
    public void show() {
        orderRepo.findAll().forEach((order) -> {
            int thePrice = order.getItems().stream().mapToInt(oi -> oi.getProduct().getPrice()*oi.getNumber()).sum();
            orderModel.addRow(new String[]{order.getOrderId()+"", order.getDeliveryAddress() + "", order.getCustomer().getName(), thePrice/100+""});
        });

        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    private void createUIComponents() {
        orderModel = new DefaultTableModel(new String[]{"ID", "Address", "Kunde", "Gesamtpreis"}, 0);
        Bestellungen = new JTable(orderModel);
    }
}
