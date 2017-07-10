package de.randomerror.GUI.controller;

import de.randomerror.GUI.view.SalesView;
import de.randomerror.services.LoginService;
import de.randomerror.util.Provided;

/**
 * Controller for the Loginview.
 */
@Provided
public class LoginViewController {

    private SalesView salesView;


    private LoginService loginService;

    /**
     * Accepts Logincredentials and passes them to the loginService
     * @param user
     * @param password
     */
    public void login(String user, String password) {
        loginService.login(user, password);
    }

    /**
     * dispatches the View corresponding to the Role of the current User
     */
    public void transitionToNextView() {
        switch (loginService.getCurrentUser().getRole()) {
            case SALES_HUMAN:
                salesView.show();
                break;

        }
    }
}
