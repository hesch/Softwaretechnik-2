package de.randomerror;

import de.randomerror.GUI.view.LoginView;
import de.randomerror.entity.*;
import de.randomerror.persistence.*;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henri on 11.04.17.
 */
@Provided
public class Main {

    ProductRepo productRepo;
    ProductClassRepo productClassRepo;
    CustomerRepo customerRepo;
    OrderRepo orderRepo;
    AddressRepo addressRepo;
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
        Product p1 = new Product(0, "Zauberwürfel", "Ein Würfel. Er ist magisch!", 1337),
                p2 = new Product(1, "Kopfhörer", "Sie hören Köpfe!", 2799),
                p3 = new Product(2, "Flammenwerfer", "Eine stylische Tischbeleuchtung!", 20000),
                p4 = new Product(3, "Benzin", "Leuchtmittel! Anderthalb drittel Gallonen", 246),
                p5 = new Product(4, "Wecker", "Montags 8 Uhr, der Klinger weckelt!", 1400),
                p6 = new Product(5, "SD Karte", "Meine ist Kaputt! :(", 13400),
                p7 = new Product(6, "Space Shuttle", "Ist der Mond aus Käse?", 1333333742),
                p8 = new Product(7, "Minimammut", "Es ist flauschig und reguliert das Raumklima!", 7);

        productRepo.save(p1);
        productRepo.save(p2);
        productRepo.save(p3);
        productRepo.save(p4);
        productRepo.save(p5);
        productRepo.save(p6);
        productRepo.save(p7);
        productRepo.save(p8);

        productClassRepo.save(new ProductClass(0, p1, 100000));
        productClassRepo.save(new ProductClass(1, p2, 10000));
        productClassRepo.save(new ProductClass(2, p3, 1000));
        productClassRepo.save(new ProductClass(3, p4, 1000));
        productClassRepo.save(new ProductClass(4, p5, 100));
        productClassRepo.save(new ProductClass(5, p6, 10));
        productClassRepo.save(new ProductClass(6, p7, 1));
        productClassRepo.save(new ProductClass(7, p8, 700000));



        Customer c1 = new Customer("Jan", new Address(3, "Emil-Figge-Strate",
                "4",
                "1337",
                "Milky Way",
                "Irguster",
                "Universe"), "jan@janface.mc", "709655430", 45345);
        customerRepo.save(c1);
        List<OrderItem> orderItems = new LinkedList<>();

        orderItems.add(new OrderItem(0,18, p1));
        orderItems.add(new OrderItem(1,6, p2));
        orderItems.add(new OrderItem(2,4, p5));

        orderRepo.save(new Order(123, orderItems, c1));

        addressRepo.save(new Address(1, "street",
                "4",
                "1337",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));
        addressRepo.findById(1);

        addressRepo.save(new Address(2, "asdf",
                "4465",
                "13371",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));

        addressRepo.save(new Address(3, "stasse",
                "123",
                "131437",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));

        List<Address> addresses = addressRepo.findAll();

        List<Order> orders = orderRepo.findAll();

        System.out.println("testdata initialized");
    }
}
