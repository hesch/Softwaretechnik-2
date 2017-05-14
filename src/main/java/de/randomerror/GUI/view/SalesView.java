package de.randomerror.GUI.view;

import de.randomerror.GUI.controller.SalesViewController;
import de.randomerror.entity.Customer;
import de.randomerror.entity.Order;
import de.randomerror.entity.OrderItem;
import de.randomerror.entity.ProductClass;
import de.randomerror.util.Provided;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by henri on 08.05.17.
 */

@Provided
public class SalesView implements View {


    private JFrame frame = new JFrame("Yukon");

    private DefaultTableModel orderModel;
    private DefaultTableModel orderItemModel;
    private DefaultTableModel inventoryModel;
    private SalesViewController controller;

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
    private JTextField nTotalPriceField;
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
    private DefaultTableModel nOrderItemModel;

    public SalesView() {
        $$$setupUI$$$();
        nCustomerField.setText("Geben sie eine Kundennummer ein um die Kundendaten abzurufen");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        orderTable.getSelectionModel().addListSelectionListener(selectionEvent -> {
            if (!selectionEvent.getValueIsAdjusting()) {
                Order order = controller.getOrderById(Integer.valueOf(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()));
                customerField.setText(order.getCustomer().getName());
                customerIdField.setText(order.getCustomer().getId() + "");
                orderIdField.setText(order.getId() + "");
                List<OrderItem> orderItems = order.getItems();
                totalpriceField.setText(order.getTotal() + "");
                orderItems.forEach(i -> orderItemModel.addRow(new String[]{i.getProduct().getId() + "", i.getProduct().getName(), i.getProduct().getDescription(), i.getProduct().getDoublePrice() + "", i.getNumber() + "", i.getTotal() + ""}));
            }
        });
        nInventoryTable.getSelectionModel().addListSelectionListener(inventorySelectionEvent -> {
            if (!inventorySelectionEvent.getValueIsAdjusting()) {
                ProductClass pc = controller.getProductClassById(Long.valueOf(nInventoryTable.getValueAt(nInventoryTable.getSelectedRow(), 0).toString()));
                nProduktIdField.setText(pc.getProduct().getId() + "");
            }
        });
        nCustomerIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!nCustomerIdField.getText().equals("")) {
                    int id = Integer.valueOf(nCustomerIdField.getText());
                    Optional<Customer> optional = controller.getCustomerById(id);
                    if (optional.isPresent()) {
                        optional.ifPresent(customer -> {
                            nCustomerField.setText(customer.getName());
                            nPhoneField.setText(customer.getPhoneNumber());
                            nAdressStreetField.setText(customer.getAddress().getStreet());
                            nAdressCityField.setText(customer.getAddress().getCity());
                            nAdressZipCodeField.setText(customer.getAddress().getZipCode());
                            nAdressStateField.setText(customer.getAddress().getState());
                            nAdressCountryField.setText(customer.getAddress().getCountry());
                        });
                    } else {
                        nCustomerField.setText("unbekannte Kundennummer");
                        clearCustomerDetails();
                    }
                } else {
                    nCustomerField.setText("Geben sie eine Kundennummer ein um die Kundendaten abzurufen");
                    clearCustomerDetails();

                }
            }
            private void clearCustomerDetails() {
                nPhoneField.setText("");
                nAdressStreetField.setText("");
                nAdressCityField.setText("");
                nAdressZipCodeField.setText("");
                nAdressStateField.setText("");
                nAdressCountryField.setText("");
            }
        });
        nAddButton.addActionListener(addEvent -> {
            if (!nProduktIdField.getText().equals("")) {
                if (Integer.valueOf(nQuantitySpinner.getValue() + "") != 0) {
                    int pid = Integer.valueOf(nProduktIdField.getText());
                    ProductClass p = controller.getProductClassById(pid);
                    int c = nOrderItemModel.getRowCount();
                    boolean contains = false;
                    int i;
                    for (i = 0; i <= c - 1; i++) {
                        if (nOrderItemsTable.getValueAt(i, 0).equals(pid + "")) {
                            contains = true;
                        }
                    }
                    if (contains) {
                        nOrderItemsTable.setValueAt(Integer.valueOf(nOrderItemsTable.getValueAt(i - 1, 4) + "") + Integer.valueOf(nQuantitySpinner.getValue() + ""), i - 1, 4);
                        nOrderItemsTable.setValueAt(Double.valueOf(nOrderItemsTable.getValueAt(i - 1, 4) + "") * Double.valueOf(nOrderItemsTable.getValueAt(i - 1, 3) + ""), i - 1, 5);
                    } else {
                        nOrderItemModel.addRow(new String[]{p.getId() + "", p.getProduct().getName(), p.getProduct().getDescription(), p.getProduct().getDoublePrice() + "", nQuantitySpinner.getValue() + "", Integer.valueOf(nQuantitySpinner.getValue() + "") * p.getProduct().getDoublePrice() + ""});
                    }
                }
            }
            nTotalPriceField.setText(IntStream.range(0, nOrderItemModel.getRowCount()).mapToDouble(i -> Double.valueOf(nOrderItemsTable.getValueAt(i, 5) + "")).sum() + "");
        });
        nSaveButton.addActionListener(saveEvent->{
//            controller.saveNewOrder();
        });
        frame.add(salespanel);
    }

    @Override
    public void show() {
        controller.getAllOrders().forEach(order -> orderModel.addRow(new String[]{order.getId() + "", order.getCustomer().getName(), order.getTotal() + ""}));
        controller.getInventory().forEach(productClass -> inventoryModel.addRow(new String[]{productClass.getProduct().getId() + "", productClass.getProduct().getName(), productClass.getProduct().getDescription(), productClass.getProduct().getDoublePrice() + "", productClass.getStock() + ""}));
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    private void createUIComponents() {
        orderModel = new DefaultTableModel(new String[]{"ID", "Address", "Kunde", "Gesamtpreis"}, 0);
        orderItemModel = new DefaultTableModel(new String[]{"ID", "Name", "Beschreibung", "Einzelpreis", "Anzahl", "Preis"}, 0);
        inventoryModel = new DefaultTableModel(new String[]{"ID", "Name", "Beschreibung", "Einzelpreis", "Bestand"}, 0);
        nOrderItemModel = new DefaultTableModel(new String[]{"ID", "Name", "Beschreibung", "Einzelpreis", "Anzahl", "Preis"}, 0);
        orderTable = new JTable(orderModel);
        orderItemTable = new JTable(orderItemModel);
        nInventoryTable = new JTable(inventoryModel);
        nOrderItemsTable = new JTable(nOrderItemModel);

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        salespanel = new JPanel();
        salespanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        salespanel.setMinimumSize(new Dimension(1280, 720));
        salespanel.setPreferredSize(new Dimension(1280, 720));
        salespanel.setRequestFocusEnabled(false);
        tabbedPane1 = new JTabbedPane();
        salespanel.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        orderSplitter = new JSplitPane();
        tabbedPane1.addTab("Bestellungen", orderSplitter);
        orderScroll = new JScrollPane();
        orderSplitter.setLeftComponent(orderScroll);
        orderTable.setFillsViewportHeight(false);
        orderScroll.setViewportView(orderTable);
        orderDetails = new JPanel();
        orderDetails.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        orderSplitter.setRightComponent(orderDetails);
        customerLabel = new JLabel();
        customerLabel.setText("Kunde:");
        orderDetails.add(customerLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerIdLabel = new JLabel();
        customerIdLabel.setText("KundenNr:");
        orderDetails.add(customerIdLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        orderIdLabel = new JLabel();
        orderIdLabel.setText("BestellungsNr:");
        orderDetails.add(orderIdLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalPriceLabel = new JLabel();
        totalPriceLabel.setText("Gesamtpreis");
        orderDetails.add(totalPriceLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerField = new JTextField();
        customerField.setEditable(false);
        orderDetails.add(customerField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        customerIdField = new JTextField();
        customerIdField.setEditable(false);
        orderDetails.add(customerIdField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        orderIdField = new JTextField();
        orderIdField.setEditable(false);
        orderDetails.add(orderIdField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        totalpriceField = new JTextField();
        totalpriceField.setEditable(false);
        orderDetails.add(totalpriceField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        orderDetails.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(orderItemTable);
        productsLabel = new JLabel();
        productsLabel.setText("Produkte");
        orderDetails.add(productsLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newOrder = new JPanel();
        newOrder.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Neue Bestellung", newOrder);
        nOrderDetailSplitter = new JSplitPane();
        nOrderDetailSplitter.setOrientation(0);
        newOrder.add(nOrderDetailSplitter, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        nCustomerDetail = new JPanel();
        nCustomerDetail.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        nOrderDetailSplitter.setLeftComponent(nCustomerDetail);
        nCustomerIdLabel = new JLabel();
        nCustomerIdLabel.setText("KundenNr:");
        nCustomerDetail.add(nCustomerIdLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nCustomeLabel = new JLabel();
        nCustomeLabel.setText("Kunde:");
        nCustomerDetail.add(nCustomeLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nCustomerIdField = new JTextField();
        nCustomerDetail.add(nCustomerIdField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nPhoneLabel = new JLabel();
        nPhoneLabel.setText("Telefon");
        nCustomerDetail.add(nPhoneLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressLabel = new JLabel();
        nAdressLabel.setText("Adresse");
        nCustomerDetail.add(nAdressLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nPhoneField = new JTextField();
        nPhoneField.setEditable(false);
        nCustomerDetail.add(nPhoneField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nCustomerField = new JTextField();
        nCustomerField.setEditable(false);
        nCustomerDetail.add(nCustomerField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressContainer = new JPanel();
        nAdressContainer.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        nCustomerDetail.add(nAdressContainer, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nAdressStreetLabel = new JLabel();
        nAdressStreetLabel.setText("Straße");
        nAdressContainer.add(nAdressStreetLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressCityLabel = new JLabel();
        nAdressCityLabel.setText("Stadt");
        nAdressContainer.add(nAdressCityLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressZipCode = new JLabel();
        nAdressZipCode.setText("Postleitzahl");
        nAdressContainer.add(nAdressZipCode, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressStateLabel = new JLabel();
        nAdressStateLabel.setText("Bundesland/Provinz");
        nAdressContainer.add(nAdressStateLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressCountryLabel = new JLabel();
        nAdressCountryLabel.setText("Land");
        nAdressContainer.add(nAdressCountryLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressStateField = new JTextField();
        nAdressStateField.setEditable(false);
        nAdressContainer.add(nAdressStateField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressZipCodeField = new JTextField();
        nAdressZipCodeField.setEditable(false);
        nAdressContainer.add(nAdressZipCodeField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressCityField = new JTextField();
        nAdressCityField.setEditable(false);
        nAdressContainer.add(nAdressCityField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressStreetField = new JTextField();
        nAdressStreetField.setEditable(false);
        nAdressContainer.add(nAdressStreetField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressCountryField = new JTextField();
        nAdressCountryField.setEditable(false);
        nAdressContainer.add(nAdressCountryField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAbortButton = new JButton();
        nAbortButton.setText("abbrechen");
        nCustomerDetail.add(nAbortButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nSaveButton = new JButton();
        nSaveButton.setText("speichern");
        nCustomerDetail.add(nSaveButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nOrderConatiner = new JPanel();
        nOrderConatiner.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        nOrderDetailSplitter.setRightComponent(nOrderConatiner);
        nOrderContainerSplitter = new JSplitPane();
        nOrderConatiner.add(nOrderContainerSplitter, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        nOrderItemsContainer = new JPanel();
        nOrderItemsContainer.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        nOrderContainerSplitter.setRightComponent(nOrderItemsContainer);
        nTotalpriceLabel = new JLabel();
        nTotalpriceLabel.setText("Summe");
        nOrderItemsContainer.add(nTotalpriceLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nTotalPriceField = new JTextField();
        nTotalPriceField.setEditable(false);
        nOrderItemsContainer.add(nTotalPriceField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        nOrderItemsContainer.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(nOrderItemsTable);
        nInventoryContainer = new JPanel();
        nInventoryContainer.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        nOrderContainerSplitter.setLeftComponent(nInventoryContainer);
        nProduktIdField = new JTextField();
        nInventoryContainer.add(nProduktIdField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nProductIdLabel = new JLabel();
        nProductIdLabel.setText("ProduktNr:");
        nInventoryContainer.add(nProductIdLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nQuantitySpinner = new JSpinner();
        nInventoryContainer.add(nQuantitySpinner, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nQuantityLabel = new JLabel();
        nQuantityLabel.setText("Menge");
        nInventoryContainer.add(nQuantityLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAddButton = new JButton();
        nAddButton.setText("hinzufügen");
        nInventoryContainer.add(nAddButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        nInventoryContainer.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(nInventoryTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return salespanel;
    }
}
