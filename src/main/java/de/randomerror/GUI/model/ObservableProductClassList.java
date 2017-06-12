package de.randomerror.GUI.model;

import de.randomerror.entity.ProductClassDTO;
import de.randomerror.persistence.DAO.ProductClassDAO;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

/**
 * Created by henri on 12.06.17.
 */
@Provided
public class ObservableProductClassList extends ObservableDataList<ProductClassDTO, ProductClassDAO> {
    @Override
    public void onInit() {
        Injector i = Injector.getInstance();
        repo = i.getProvided(ProductClassDAO.class);
        super.onInit();
    }
}
