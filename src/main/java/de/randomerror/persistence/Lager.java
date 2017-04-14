package de.randomerror.persistence;

import de.randomerror.GUI.Frame;
import de.randomerror.util.Provided;

/**
 * Created by Henri on 11.04.17.
 */
@Provided
public class Lager {
    public Frame f;

    public void testMethod() {
        System.out.println("ich bin eine testmethode im Lager");
        System.out.println("hoffentlich ist mein Frame nicht null: " + f.test());
    }
}
