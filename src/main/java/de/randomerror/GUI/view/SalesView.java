package de.randomerror.GUI.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import de.randomerror.GUI.controller.SalesViewController;
import de.randomerror.entity.CustomerDTO;
import de.randomerror.entity.OrderDTO;
import de.randomerror.entity.OrderItemDTO;
import de.randomerror.entity.ProductClassDTO;
import de.randomerror.services.TranslationService;
import de.randomerror.util.Provided;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * View to display Data and accept user Input to modify it.
 */

@Log4j2
@Provided
public class SalesView implements View {
    private TranslationService t = TranslationService.getInstance();

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
    private JLabel nCustomerLabel;
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        addTabbedPanelChangeListener();
        addOrderTableListSelectionListener();
        addInventoryListSelectionListener();
        addFocusLostListener();
        addNewOrderAddActionListener();
        addSaveActionListener();
        addAbortActionListener();
        frame.add(salespanel);
    }

    /**
     * adds a changelistener to the tabbedPanel Component
     */
    private void addTabbedPanelChangeListener() {
        tabbedPane1.addChangeListener(l -> {
            try {
                while (true) {
                    orderModel.removeRow(0);
                }
            } catch (ArrayIndexOutOfBoundsException e) { /* nothing to see here...everything is normal*/}
            controller.getAllOrders().forEach(order -> orderModel.addRow(new String[]{order.getId() + "", order.getCustomer().getAddress() + "", order.getCustomer().getName(), order.getTotal() + ""}));
        });
    }

    /**
     * adds ListSelectionlistener to the orderTable Component
     */
    private void addOrderTableListSelectionListener() {
        orderTable.getSelectionModel().addListSelectionListener(selectionEvent -> {
            if (!selectionEvent.getValueIsAdjusting() && orderTable.getSelectedRow() >= 0) {
                OrderDTO order = controller.getOrderById(Long.valueOf(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()));
                try {
                    while (true) {
                        orderItemModel.removeRow(0);
                    }
                } catch (ArrayIndexOutOfBoundsException e) { /* nothing to see here...everything is normal*/}

                customerField.setText(order.getCustomer().getName());
                customerIdField.setText(order.getCustomer().getId() + "");
                orderIdField.setText(order.getId() + "");
                List<OrderItemDTO> orderItems = order.getItems();
                totalpriceField.setText(order.getTotal() + "");
                orderItems.forEach(i -> orderItemModel.addRow(new String[]{i.getProduct().getId() + "", i.getProduct().getName(), i.getProduct().getDescription(), i.getProduct().getDoublePrice() + "", i.getNumber() + "", i.getTotal() + ""}));
            }
        });
    }

    /**
     * adds ListSelectionlistener to the nInventoryTable Component
     */
    private void addInventoryListSelectionListener() {
        nInventoryTable.getSelectionModel().addListSelectionListener(inventorySelectionEvent -> {
            if (!inventorySelectionEvent.getValueIsAdjusting()) {
                ProductClassDTO pc = controller.getProductClassById(Long.valueOf(nInventoryTable.getValueAt(nInventoryTable.getSelectedRow(), 0).toString()));
                nProduktIdField.setText(pc.getProduct().getId() + "");
            }
        });
    }

    /**
     * adds a FocusListener to the nCustomerIdFiled Components. The Listener loads a Customer using the SalesSevice
     */
    private void addFocusLostListener() {
        nCustomerIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!nCustomerIdField.getText().equals("")) {
                    int id = Integer.valueOf(nCustomerIdField.getText());
                    Optional<CustomerDTO> optional = controller.getCustomerById(id);
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
                        nCustomerField.setText(t.translate("SALES.CUSTOMER_INFO.UNKNOWN"));
                        clearCustomerDetails();
                    }
                } else {
                    nCustomerField.setText(t.translate("SALES.CUSTOMER_INFO"));
                    clearCustomerDetails();
                }
            }

            /**
             * clears the Fields containing the customer Info
             */
            private void clearCustomerDetails() {
                nPhoneField.setText("");
                nAdressStreetField.setText("");
                nAdressCityField.setText("");
                nAdressZipCodeField.setText("");
                nAdressStateField.setText("");
                nAdressCountryField.setText("");
            }
        });
    }

    /**
     * adds an Actionlistener to the nAddButton
     */
    private void addNewOrderAddActionListener() {
        nAddButton.addActionListener(addEvent -> {
            if (!nProduktIdField.getText().equals("")) {
                if (Integer.valueOf(nQuantitySpinner.getValue() + "") != 0) {
                    int pid = Integer.valueOf(nProduktIdField.getText());
                    ProductClassDTO p = controller.getProductClassById(pid);
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
    }

    /**
     * adds an ActionListener to the nSaveButton
     */
    private void addSaveActionListener() {
        nSaveButton.addActionListener(saveEvent -> {
            int id = Integer.valueOf(nCustomerIdField.getText());
            Optional<CustomerDTO> optional = controller.getCustomerById(id);
            if (optional.isPresent() && Double.valueOf(nTotalPriceField.getText()) >= 0) {
                List<OrderItemDTO> orderItems = new LinkedList<OrderItemDTO>();
                int c = nOrderItemModel.getRowCount();
                for (int i = 0; i <= c - 1; i++) {
                    orderItems.add(new OrderItemDTO(Integer.valueOf(nOrderItemsTable.getValueAt(i, 4) + ""), controller.getProductClassById(Integer.valueOf(nOrderItemsTable.getValueAt(i, 0) + "")).getProduct()));
                }
                controller.saveNewOrder(Integer.valueOf(nCustomerIdField.getText()), orderItems);
            }
        });
    }

    /**
     * adds an Actionlistener to the nAbortButton
     */
    private void addAbortActionListener() {
        nAbortButton.addActionListener(abortEvent -> {
            nPhoneField.setText("");
            nAdressStreetField.setText("");
            nAdressCityField.setText("");
            nAdressZipCodeField.setText("");
            nAdressStateField.setText("");
            nAdressCountryField.setText("");
            nCustomerField.setText(t.translate("SALES.CUSTOMER_INFO"));
            try {
                while (true) {
                    nOrderItemModel.removeRow(0);
                }
            } catch (ArrayIndexOutOfBoundsException e) { /* nothing to see here...all is normal*/}
        });
    }

    /**
     * makes the frame visible and initiates some Datastructures
     */
    @Override
    public void show() {
        controller.getAllOrders().forEach(order -> orderModel.addRow(new String[]{order.getId() + "", order.getCustomer().getAddress() + "", order.getCustomer().getName(), order.getTotal() + ""}));
        controller.getInventory().forEach(productClass -> inventoryModel.addRow(new String[]{productClass.getProduct().getId() + "", productClass.getProduct().getName(), productClass.getProduct().getDescription(), productClass.getProduct().getDoublePrice() + "", productClass.getStock() + ""}));
        frame.setVisible(true);
    }

    /**
     * hides the frame
     */
    @Override
    public void hide() {
        frame.setVisible(false);
    }

    /**
     * creates and initializes the UIComponents contained in the view.
     */
    private void createUIComponents() {
        orderModel = new DefaultTableModel(new String[]{t.translate("SALES.ORDER.ID"), t.translate("SALES.ORDER.ADDRESS"), t.translate("SALES.ORDER.CUSTOMER"), t.translate("SALES.ORDER.PRICE")}, 0);
        orderItemModel = new DefaultTableModel(new String[]{t.translate("SALES.ORDER.ID"), t.translate("SALES.PRODUCT.NAME"), t.translate("SALES.PRODUCT.DESCRIPTION"), t.translate("SALES.PRODUCT.SINGLE_PRICE"), t.translate("SALES.PRODUCT.AMOUNT"), t.translate("SALES.PRODUCT.PRICE")}, 0);
        inventoryModel = new DefaultTableModel(new String[]{t.translate("SALES.ORDER.ID"), t.translate("SALES.PRODUCT.NAME"), t.translate("SALES.PRODUCT.DESCRIPTION"), t.translate("SALES.PRODUCT.SINGLE_PRICE"), t.translate("SALES.PRODUCT.STOCK")}, 0);
        nOrderItemModel = new DefaultTableModel(new String[]{t.translate("SALES.ORDER.ID"), t.translate("SALES.PRODUCT.NAME"), t.translate("SALES.PRODUCT.DESCRIPTION"), t.translate("SALES.PRODUCT.SINGLE_PRICE"), t.translate("SALES.PRODUCT.AMOUNT"), t.translate("SALES.PRODUCT.PRICE")}, 0);
        orderTable = new JTable(orderModel);
        orderItemTable = new JTable(orderItemModel);
        nInventoryTable = new JTable(inventoryModel);
        nOrderItemsTable = new JTable(nOrderItemModel);

        JMenuBar languageMenuBar = new JMenuBar();
        JMenu languageMenu = new JMenu("Language");

        JMenuItem eng = new JMenuItem("English"),
                ger = new JMenuItem("German");

        languageMenu.add(eng);
        languageMenu.add(ger);

        eng.addActionListener(ev -> {
            t.setLanguage("en");
            log.info("Setting Language to EN");
            onInit();
        });

        ger.addActionListener(ev -> {
            t.setLanguage("de");
            log.info("Setting Language to DE");
            onInit();
        });

        languageMenuBar.add(languageMenu);
        frame.setJMenuBar(languageMenuBar);
    }

    public void onInit() {
        nAddButton.setText(t.translate("SALES.NEW_ORDER.ADD"));
        nAbortButton.setText(t.translate("SALES.NEW_ORDER.CANCEL"));
        nSaveButton.setText(t.translate("SALES.NEW_ORDER.SAVE"));
        customerLabel.setText(t.translate("SALES.ORDER.CUSTOMER"));
        customerIdLabel.setText(t.translate("SALES.ORDER_DETAIL.CUSTOMER_ID"));
        orderIdLabel.setText(t.translate("SALES.ORDER_DETAIL.ORDER_ID"));
        totalPriceLabel.setText(t.translate("SALES.NEW_ORDER.SUM"));
        productsLabel.setText(t.translate("SALES.ORDER_DETAIL.PRODUCTS"));
        nCustomerIdLabel.setText(t.translate("SALES.ORDER_DETAIL.CUSTOMER_ID"));
        nCustomerLabel.setText(t.translate("SALES.ORDER_DETAIL.CUSTOMER"));
        nPhoneLabel.setText(t.translate("SALES.NEW_ORDER.TELEPHONE"));
        nAdressLabel.setText(t.translate("SALES.ORDER.ADDRESS"));
        nTotalpriceLabel.setText(t.translate("SALES.NEW_ORDER.SUM"));
        nProductIdLabel.setText(t.translate("SALES.PRODUCT.ID"));
        nQuantityLabel.setText(t.translate("SALES.PRODUCT.AMOUNT"));
        nAdressStreetLabel.setText(t.translate("SALES.ADDRESS.STREET"));
        nAdressCityLabel.setText(t.translate("SALES.ADDRESS.CITY"));
        nAdressZipCode.setText(t.translate("SALES.ADDRESS.ZIP"));
        nAdressStateLabel.setText(t.translate("SALES.ADDRESS.PROVINCE"));
        nAdressCountryLabel.setText(t.translate("SALES.ADDRESS.COUNTRY"));
        nCustomerField.setText(t.translate("SALES.CUSTOMER_INFO"));

        tabbedPane1.setTitleAt(0, t.translate("SALES.ORDER.TITLE"));
        tabbedPane1.setTitleAt(1, t.translate("SALES.NEW_ORDER.TITLE"));
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
        salespanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        salespanel.setMinimumSize(new Dimension(1280, 720));
        salespanel.setPreferredSize(new Dimension(1280, 720));
        salespanel.setRequestFocusEnabled(false);
        tabbedPane1 = new JTabbedPane();
        salespanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        orderSplitter = new JSplitPane();
        orderSplitter.setContinuousLayout(true);
        orderSplitter.setDividerLocation(453);
        tabbedPane1.addTab("Bestellungen", orderSplitter);
        orderScroll = new JScrollPane();
        orderSplitter.setLeftComponent(orderScroll);
        orderTable.setFillsViewportHeight(false);
        orderScroll.setViewportView(orderTable);
        orderDetails = new JPanel();
        orderDetails.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        orderSplitter.setRightComponent(orderDetails);
        customerLabel = new JLabel();
        customerLabel.setText("Kunde:");
        orderDetails.add(customerLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerIdLabel = new JLabel();
        customerIdLabel.setText("KundenNr:");
        orderDetails.add(customerIdLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        orderIdLabel = new JLabel();
        orderIdLabel.setText("BestellungsNr:");
        orderDetails.add(orderIdLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalPriceLabel = new JLabel();
        totalPriceLabel.setText("Gesamtpreis");
        orderDetails.add(totalPriceLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerField = new JTextField();
        customerField.setEditable(false);
        orderDetails.add(customerField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        customerIdField = new JTextField();
        customerIdField.setEditable(false);
        orderDetails.add(customerIdField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        orderIdField = new JTextField();
        orderIdField.setEditable(false);
        orderDetails.add(orderIdField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        totalpriceField = new JTextField();
        totalpriceField.setEditable(false);
        orderDetails.add(totalpriceField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        orderDetails.add(scrollPane1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(orderItemTable);
        productsLabel = new JLabel();
        productsLabel.setText("Produkte");
        orderDetails.add(productsLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newOrder = new JPanel();
        newOrder.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Neue Bestellung", newOrder);
        nOrderDetailSplitter = new JSplitPane();
        nOrderDetailSplitter.setOrientation(0);
        newOrder.add(nOrderDetailSplitter, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        nCustomerDetail = new JPanel();
        nCustomerDetail.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        nOrderDetailSplitter.setLeftComponent(nCustomerDetail);
        nCustomerIdLabel = new JLabel();
        nCustomerIdLabel.setText("KundenNr:");
        nCustomerDetail.add(nCustomerIdLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nCustomerLabel = new JLabel();
        nCustomerLabel.setText("Kunde:");
        nCustomerDetail.add(nCustomerLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nCustomerIdField = new JTextField();
        nCustomerIdField.setText("");
        nCustomerDetail.add(nCustomerIdField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nPhoneLabel = new JLabel();
        nPhoneLabel.setText("Telefon");
        nCustomerDetail.add(nPhoneLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressLabel = new JLabel();
        nAdressLabel.setText("Adresse");
        nCustomerDetail.add(nAdressLabel, new GridConstraints(3, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nPhoneField = new JTextField();
        nPhoneField.setEditable(false);
        nCustomerDetail.add(nPhoneField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nCustomerField = new JTextField();
        nCustomerField.setEditable(false);
        nCustomerDetail.add(nCustomerField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressContainer = new JPanel();
        nAdressContainer.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        nCustomerDetail.add(nAdressContainer, new GridConstraints(3, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nAdressStreetLabel = new JLabel();
        nAdressStreetLabel.setText("Straße");
        nAdressContainer.add(nAdressStreetLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressCityLabel = new JLabel();
        nAdressCityLabel.setText("Stadt");
        nAdressContainer.add(nAdressCityLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressZipCode = new JLabel();
        nAdressZipCode.setText("Postleitzahl");
        nAdressContainer.add(nAdressZipCode, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressStateLabel = new JLabel();
        nAdressStateLabel.setText("Bundesland/Provinz");
        nAdressContainer.add(nAdressStateLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressCountryLabel = new JLabel();
        nAdressCountryLabel.setText("Land");
        nAdressContainer.add(nAdressCountryLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAdressStateField = new JTextField();
        nAdressStateField.setEditable(false);
        nAdressContainer.add(nAdressStateField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressZipCodeField = new JTextField();
        nAdressZipCodeField.setEditable(false);
        nAdressContainer.add(nAdressZipCodeField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressCityField = new JTextField();
        nAdressCityField.setEditable(false);
        nAdressContainer.add(nAdressCityField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressStreetField = new JTextField();
        nAdressStreetField.setEditable(false);
        nAdressContainer.add(nAdressStreetField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAdressCountryField = new JTextField();
        nAdressCountryField.setEditable(false);
        nAdressContainer.add(nAdressCountryField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nAbortButton = new JButton();
        nAbortButton.setText("abbrechen");
        nCustomerDetail.add(nAbortButton, new GridConstraints(3, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nSaveButton = new JButton();
        nSaveButton.setText("speichern");
        nCustomerDetail.add(nSaveButton, new GridConstraints(0, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nOrderConatiner = new JPanel();
        nOrderConatiner.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        nOrderDetailSplitter.setRightComponent(nOrderConatiner);
        nOrderContainerSplitter = new JSplitPane();
        nOrderConatiner.add(nOrderContainerSplitter, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        nOrderItemsContainer = new JPanel();
        nOrderItemsContainer.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        nOrderContainerSplitter.setRightComponent(nOrderItemsContainer);
        nTotalpriceLabel = new JLabel();
        nTotalpriceLabel.setText("Summe");
        nOrderItemsContainer.add(nTotalpriceLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nTotalPriceField = new JTextField();
        nTotalPriceField.setEditable(false);
        nOrderItemsContainer.add(nTotalPriceField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        nOrderItemsContainer.add(scrollPane2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(nOrderItemsTable);
        nInventoryContainer = new JPanel();
        nInventoryContainer.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        nOrderContainerSplitter.setLeftComponent(nInventoryContainer);
        nProduktIdField = new JTextField();
        nInventoryContainer.add(nProduktIdField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nProductIdLabel = new JLabel();
        nProductIdLabel.setText("ProduktNr:");
        nInventoryContainer.add(nProductIdLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nQuantitySpinner = new JSpinner();
        nInventoryContainer.add(nQuantitySpinner, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nQuantityLabel = new JLabel();
        nQuantityLabel.setText("Menge");
        nInventoryContainer.add(nQuantityLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nAddButton = new JButton();
        nAddButton.setText("hinzufügen");
        nInventoryContainer.add(nAddButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        nInventoryContainer.add(scrollPane3, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(nInventoryTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return salespanel;
    }
}
