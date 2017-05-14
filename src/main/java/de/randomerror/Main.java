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
    DeliveryHumanRepo deliveryHumanRepo;
    DeliveryRepo deliveryRepo;
    ProductRepo productRepo;
    ProductClassRepo productClassRepo;
    CustomerRepo customerRepo;
    OrderRepo orderRepo;
    OrderItemRepo orderItemRepo;
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
        Product p1 = new Product("Zauberwürfel", "Ein Würfel. Er ist magisch!", 1337),
                p2 = new Product("Kopfhörer", "Sie hören Köpfe!", 2799),
                p3 = new Product("Flammenwerfer", "Eine stylische Tischbeleuchtung!", 20000),
                p4 = new Product("Benzin", "Leuchtmittel! Anderthalb drittel Gallonen", 246),
                p5 = new Product("Wecker", "Montags 8 Uhr, der Klinger weckelt!", 1400),
                p6 = new Product("SD Karte", "Meine ist Kaputt! :(", 13400),
                p7 = new Product("Space Shuttle", "Ist der Mond aus Käse?", 1333333742),
                p8 = new Product("Minimammut", "Es ist flauschig und reguliert das Raumklima!", 7);

        productRepo.save(p1);
        productRepo.save(p2);
        productRepo.save(p3);
        productRepo.save(p4);
        productRepo.save(p5);
        productRepo.save(p6);
        productRepo.save(p7);
        productRepo.save(p8);

        productClassRepo.save(new ProductClass(p1, 100000));
        productClassRepo.save(new ProductClass(p2, 10000));
        productClassRepo.save(new ProductClass(p3, 1000));
        productClassRepo.save(new ProductClass(p4, 1000));
        productClassRepo.save(new ProductClass(p5, 100));
        productClassRepo.save(new ProductClass(p6, 10));
        productClassRepo.save(new ProductClass(p7, 1));
        productClassRepo.save(new ProductClass(p8, 700000));


//        DeliveryHuman dh1 = new DeliveryHuman("Mr. Meeseeks",
//                new Address("street",
//                        "3",
//                        "1337",
//                        "Milky Way",
//                        "Irgendwas mit Supercluster",
//                        "Universe"),
//                "meeseeks@lookatmee.universe",
//                "01234883020394"),
//                dh2 = new DeliveryHuman( "Jannis Kaiser",
//                        new Address( "street",
//                                "4",
//                                "1337",
//                                "Milky Way",
//                                "Irgendwas mit Supercluster",
//                                "Universe"),
//                        "kaiser@königsmensch.kaiserreich",
//                        "0112346520394"),
//                dh3 = new DeliveryHuman( "Fishermans Friend", new Address("street",
//                        "3",
//                        "1337",
//                        "Milky Way",
//                        "Irgendwas mit Supercluster",
//                        "Universe"),
//                        "friend@fishmebabyonemoretime.sealevelsarerising",
//                        "505505505505505");
//
//        deliveryHumanRepo.save(dh1);
//        deliveryHumanRepo.save(dh2);
//        deliveryHumanRepo.save(dh3);


//        List<DeliveryItem> items = new LinkedList<>();
//
//        items.add(new DeliveryItem( 15, p1, 1200));
//        items.add(new DeliveryItem( 300, p7, 7000));
//
//        deliveryRepo.save(new Delivery( "YK-10024", items, dh3));
//
//        items = new LinkedList<>();
//
//        items.add(new DeliveryItem(30000, p4, 100));
//
//        deliveryRepo.save(new Delivery("YK-10507", items, dh2));

        Address c1a = new Address("Emil-Figge-Strate",
                "4",
                "1337",
                "Milky Way",
                "Irguster",
                "Universe");
        addressRepo.save(c1a);

        System.out.println("id after addressRepo save: " + c1a.getId());
        Customer c1 = new Customer("Jan", c1a, "jan@janface.mc", "709655430");
        customerRepo.save(c1);

        List<OrderItem> orderItems = new LinkedList<>();

        orderItems.add(new OrderItem(18, p1));
        orderItems.add(new OrderItem(6, p2));
        orderItems.add(new OrderItem(4, p5));

        Order o = new Order();
        o.setCustomer(c1);
        orderRepo.save(o);
        o.setItems(orderItems);

        orderItems.forEach(orderItem -> orderItemRepo.save(orderItem));

        addressRepo.save(new Address("street",
                "4",
                "1337",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));
        addressRepo.findById(1);

        addressRepo.save(new Address("asdf",
                "4465",
                "13371",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));

        addressRepo.save(new Address("stasse",
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
