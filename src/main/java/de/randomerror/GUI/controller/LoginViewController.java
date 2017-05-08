package de.randomerror.GUI.controller;

import de.randomerror.GUI.exceptions.CredentialsInvalidException;
import de.randomerror.GUI.view.SalesView;
import de.randomerror.GUI.view.SupplyView;
import de.randomerror.GUI.view.WarehouseView;
import de.randomerror.entity.Role;
import de.randomerror.entity.User;
import de.randomerror.entity.Warehouse;
import de.randomerror.persistence.UserRepo;
import de.randomerror.services.LoginService;
import de.randomerror.util.Provided;
import lombok.Getter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class LoginViewController {
    public WarehouseView warehouseView;
    public SalesView salesView;
    public SupplyView supplyView;
    public LoginService loginService;

    public void login(String user, String pass) {
        loginService.login(user, pass);
    }

    public void transitionToNextView() {
        switch (loginService.getCurrentUser().getRole()) {
            case SUPPLY_HUMAN:
                supplyView.show();
                break;
            case SALES_HUMAN:
                salesView.show();
                break;
            case WAREHOUSE_HUMAN:
                warehouseView.show();
                break;
        }
    }
}
