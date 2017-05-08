package de.randomerror;

import de.randomerror.GUI.view.LoginView;
import de.randomerror.entity.Delivery;
import de.randomerror.persistence.DeliveryHumanRepo;
import de.randomerror.persistence.DeliveryRepo;
import de.randomerror.persistence.ProductClassRepo;
import de.randomerror.persistence.ProductRepo;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

import java.io.IOException;

/**
 * Created by Henri on 11.04.17.
 */
public class Main {
    DeliveryHumanRepo deliveryHumanRepo;
    DeliveryRepo deliveryRepo;
    ProductRepo productRepo;
    ProductClassRepo productClassRepo;
    LoginView view;

    public static void main(String[] args) throws IOException {
        Injector.init();
        Injector.getProvided(Main.class).init();
    }

    public void init() {
        generateTestData();
        view.show();
    }

    public void generateTestData() {

    }
}
