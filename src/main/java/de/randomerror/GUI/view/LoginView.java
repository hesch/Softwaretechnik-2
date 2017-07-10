package de.randomerror.GUI.view;

import de.randomerror.GUI.controller.LoginViewController;
import de.randomerror.GUI.exceptions.CredentialsInvalidException;
import de.randomerror.services.TranslationService;
import de.randomerror.util.Provided;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
@Provided
public class LoginView implements View {
    public TranslationService t;
    public LoginViewController controller;

    private JFrame frame = new JFrame();

    private JLabel infoLabel = new JLabel();

    private JLabel nameLabel = new JLabel();
    private JLabel passwordLabel = new JLabel();

    private JTextField nameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);

    private JButton loginButton = new JButton();

    public LoginView() {
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.ipadx = 20;
        constraints.ipady = 10;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        frame.add(infoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        frame.add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        frame.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        frame.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        frame.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        frame.add(loginButton, constraints);
        frame.getRootPane().setDefaultButton(loginButton);
        loginButton.setMnemonic('L');
        loginButton.addActionListener((event) -> {
            String username = nameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                controller.login(username, password);
            } catch(CredentialsInvalidException e) {
                infoLabel.setForeground(Color.red);
                infoLabel.setText(e.getMessage());
                infoLabel.setVisible(true);
                frame.pack();
                return;
            }

            hide();
            controller.transitionToNextView();
        });
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    public void onInit() {
        frame.setTitle(t.translate("LOGIN.TITLE"));
        infoLabel.setText(t.translate("LOGIN.INFO"));

        nameLabel.setText(t.translate("LOGIN.NAME") + ":");
        passwordLabel.setText(t.translate("LOGIN.PASSWORD") + ":");
        loginButton.setText(t.translate("LOGIN.BUTTON"));

        frame.pack();
    }
}
