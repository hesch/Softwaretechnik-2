package de.randomerror.GUI.controller;

import de.randomerror.GUI.view.SalesView;
import de.randomerror.GUI.view.WarehouseView;
import de.randomerror.services.LoginService;
import de.randomerror.util.Provided;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class LoginViewController {
    public WarehouseView warehouseView;
    public SalesView salesView;

    public LoginService loginService;

    public void login(String user, String pass) {
        loginService.login(user, pass);
    }

    public void transitionToNextView() {
        switch (loginService.getCurrentUser().getRole()) {
            case SUPPLY_HUMAN:
//                supplyView.show();
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
