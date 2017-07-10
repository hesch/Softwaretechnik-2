package de.randomerror;

import de.randomerror.GUI.model.ObservableCustomerList;
import de.randomerror.GUI.model.ObservableDataList;
import de.randomerror.GUI.model.ObservableOrderList;
import de.randomerror.GUI.model.ObservableProductClassList;
import de.randomerror.GUI.view.LoginView;
import de.randomerror.entity.*;
import de.randomerror.persistence.*;
import de.randomerror.persistence.DAO.*;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


@Provided
public class Main {

    ProductDAO productRepo;
    ProductClassDAO productClassRepo;
    CustomerDAO customerRepo;
    OrderDAO orderRepo;
    OrderItemDAO orderItemRepo;
    AddressDAO addressRepo;
    LoginView view;

    ObservableCustomerList customerList;
    ObservableOrderList orderList;
    ObservableProductClassList productClassList;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("stuff");
        Injector.getInstance().init();
        Injector.getInstance().getProvided(Main.class).init();
    }

    public void init() {
        generateTestData();
        view.show();
    }

    public void generateTestData() {
        ProductDTO p1 = new ProductDTO("Zauberwürfel", "Ein Würfel. Er ist magisch!", 1337),
                p2 = new ProductDTO("Kopfhörer", "Sie hören Köpfe!", 2799),
                p3 = new ProductDTO("Flammenwerfer", "Eine stylische Tischbeleuchtung!", 20000),
                p4 = new ProductDTO("Benzin", "Leuchtmittel! Anderthalb drittel Gallonen", 246),
                p5 = new ProductDTO("Wecker", "Montags 8 Uhr, der Klinger weckelt!", 1400),
                p6 = new ProductDTO("SD Karte", "Meine ist Kaputt! :(", 13400),
                p7 = new ProductDTO("Space Shuttle", "Ist der Mond aus Käse?", 1333333742),
                p8 = new ProductDTO("Minimammut", "Es ist flauschig und reguliert das Raumklima!", 700);

        productRepo.save(p1);
        productRepo.save(p2);
        productRepo.save(p3);
        productRepo.save(p4);
        productRepo.save(p5);
        productRepo.save(p6);
        productRepo.save(p7);
        productRepo.save(p8);

        productClassRepo.save(new ProductClassDTO(p1, 100000));
        productClassRepo.save(new ProductClassDTO(p2, 10000));
        productClassRepo.save(new ProductClassDTO(p3, 1000));
        productClassRepo.save(new ProductClassDTO(p4, 1000));
        productClassRepo.save(new ProductClassDTO(p5, 100));
        productClassRepo.save(new ProductClassDTO(p6, 10));
        productClassRepo.save(new ProductClassDTO(p7, 1));
        productClassRepo.save(new ProductClassDTO(p8, 700000));




        AddressDTO c1a = new AddressDTO("Emil-Figge-Strate",
                "4",
                "1337",
                "Milky Way",
                "Irguster",
                "Universe");
        addressRepo.save(c1a);

        CustomerDTO c1 = new CustomerDTO("Jan", c1a, "jan@stuff.com", "709655430");
        customerRepo.save(c1);

        List<OrderItemDTO> orderItems = new LinkedList<>();

        orderItems.add(new OrderItemDTO(18, p1));
        orderItems.add(new OrderItemDTO(6, p2));
        orderItems.add(new OrderItemDTO(4, p5));

        OrderDTO o = new OrderDTO();
        o.setCustomer(c1);
        orderRepo.save(o);
        o.setItems(orderItems);

        orderItems.forEach(orderItem -> orderItemRepo.save(orderItem));

        addressRepo.save(new AddressDTO("street",
                "4",
                "1337",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));
        addressRepo.findById(1);

        addressRepo.save(new AddressDTO("asdf",
                "4465",
                "13371",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));

        addressRepo.save(new AddressDTO("stasse",
                "123",
                "131437",
                "Milky Way",
                "Irgendwas mit Supercluster",
                "Universe"));

        List<AddressDTO> addresses = addressRepo.findAll();

        List<OrderDTO> orders = orderRepo.findAll();

        System.out.println("testdata initialized");


        System.out.println("Reinitializing ObservableDataLists");

        Stream.of(customerList, orderList, productClassList).forEach(ObservableDataList::onInit);
    }
}
