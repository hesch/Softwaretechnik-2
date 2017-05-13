package de.randomerror.GUI.view;

import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;
import de.randomerror.persistence.OrderRepo;
import de.randomerror.util.Provided;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class SalesView implements View {
    public OrderRepo orderRepo;

    private JFrame frame = new JFrame("Yukon");

    private DefaultTableModel orderModel;
    private DefaultTableModel orderItemModel;

    private JPanel salespanel;
    private JTable orderTable;
    private JTable orderItemTable;
    private JTextField customerField;
    private JTextField customerIdField;
    private JTextField orderIdField;
    private JTextField totalpriceField;
    private JScrollPane orderScroll;
    private JTabbedPane tabbedPane1;
    private JTextField nProduktIdField;
    private JSpinner nQuantitySpinner;
    private JButton nAddButton;
    private JTable nInventoryTable;
    private JTextField nCustomerIdField;
    private JTextField nPhoneField;
    private JTextField nCustomerField;
    private JTextField nAdressZipCodeField;
    private JTextField nAdressCountryField;
    private JTextField nAdressCityField;
    private JTextField nAdressStreetField;
    private JTable nOrderItemsTable;
    private JTextField nTotalPriceLabel;
    private JButton nAbortButton;
    private JButton nSaveButton;
    private JPanel orderDetails;
    private JLabel customerLabel;
    private JLabel customerIdLabel;
    private JLabel orderIdLabel;
    private JLabel totalPriceLabel;
    private JLabel productsLabel;
    private JSplitPane nOrderDetailSplitter;
    private JPanel nCustomerDetail;
    private JLabel nCustomerIdLabel;
    private JLabel nCustomeLabel;
    private JLabel nPhoneLabel;
    private JLabel nAdressLabel;
    private JPanel nAdressContainer;
    private JPanel nOrderConatiner;
    private JSplitPane nOrderContainerSplitter;
    private JPanel nOrderItemsContainer;
    private JLabel nTotalpriceLabel;
    private JPanel nInventoryContainer;
    private JLabel nProductIdLabel;
    private JLabel nQuantityLabel;
    private JLabel nAdressStreetLabel;
    private JLabel nAdressCityLabel;
    private JLabel nAdressZipCode;
    private JLabel nAdressStateLabel;
    private JLabel nAdressCountryLabel;
    private JTextField nAdressStateField;
    private JSplitPane orderSplitter;
    private JPanel newOrder;

    public SalesView() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        orderTable.getSelectionModel().addListSelectionListener(listener -> {
            Order order = OrderRepo.findById(Long.valueOf(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()));
            customerField.setText(order.getCustomer().getName());
            customerIdField.setText(order.getCustomer().getCustomerId() + "");
            orderIdField.setText(order.getOrderId() + "");
            List<OrderItem> orderItems = order.getItems();
            totalpriceField.setText(order.getTotal()+"");
            orderItems.forEach(i -> orderItemModel.addRow(new String[]{i.getProduct().getProductId() + "",i.getProduct().getName(),i.getProduct().getDescription(),i.getProduct().getDoublePrice()+"", i.getNumber() + "",i.getTotal()+""}));
        });
        frame.add(salespanel);
    }

    @Override
    public void show() {
        orderRepo.findAll().forEach((order) -> {

            orderModel.addRow(new String[]{order.getOrderId() + "", order.getDeliveryAddress() + "", order.getCustomer().getName(), order.getTotal()+ ""});
        });

        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    private void createUIComponents() {
        orderModel = new DefaultTableModel(new String[]{"ID", "Address", "Kunde", "Gesamtpreis"}, 0);
        orderItemModel = new DefaultTableModel(new String[]{"ID", "Name","Beschreibung","Einzelpreis","Anzahl", "Preis"},0);
        orderTable = new JTable(orderModel);
        orderItemTable = new JTable(orderItemModel);
    }
}
