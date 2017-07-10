package de.randomerror.GUI.controller;

import de.randomerror.GUI.view.SalesView;
import de.randomerror.services.LoginService;
import de.randomerror.util.Provided;

/**
 *
 *
 */
@Provided
public class LoginViewController {

    private SalesView salesView;


    private LoginService loginService;

    public void login(String user, String pass) {
        loginService.login(user, pass);
    }

    public void transitionToNextView() {
        switch (loginService.getCurrentUser().getRole()) {
            case SALES_HUMAN:
                salesView.show();
                break;

        }
    }
}
