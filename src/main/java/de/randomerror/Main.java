package de.randomerror;

import de.randomerror.GUI.view.LoginView;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

import java.io.IOException;

/**
 * Created by Henri on 11.04.17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Injector.init();
        Injector.getProvided(LoginView.class).show();
    }
}
