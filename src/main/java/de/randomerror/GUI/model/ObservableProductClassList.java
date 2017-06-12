package de.randomerror.GUI.model;

import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.util.Injector;

/**
 * Created by henri on 12.06.17.
 */
public class ObservableProductClassList extends ObservableDataList<ProductClassDTO, ProductClassDAO> {
    @Override
    public void onInit() {
        Injector i = Injector.getInstance();
        repo = i.getProvided(ProductClassDAO.class);
        super.onInit();
    }
}
