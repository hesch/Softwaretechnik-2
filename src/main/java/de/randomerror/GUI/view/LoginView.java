package de.randomerror.GUI.view;

import de.randomerror.GUI.controller.LoginViewController;
import de.randomerror.GUI.exceptions.CredentialsInvalidException;
import de.randomerror.util.Provided;

import javax.swing.*;
import java.awt.*;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class LoginView implements View {
    public LoginViewController controller;

    private JFrame frame = new JFrame("login");

    private JLabel infoLabel = new JLabel();

    private JLabel nameLabel = new JLabel("Name:");
    private JLabel passwordLabel = new JLabel("Password:");

    private JTextField nameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);

    private JButton loginButton = new JButton("Login");

    public LoginView() {
        infoLabel.setForeground(Color.red);
        infoLabel.setVisible(false);

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

        loginButton.setMnemonic('L');
        loginButton.addActionListener((event) -> {
            String username = nameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                controller.login(username, password);
            } catch(CredentialsInvalidException e) {
                infoLabel.setText(e.getMessage());
                infoLabel.setVisible(true);
                frame.pack();
                return;
            }

            hide();
            controller.transitionToNextView();
        });

        frame.pack();
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }
}
