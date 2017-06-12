package de.randomerror.GUI.model;

import de.randomerror.entity.CustomerDTO;
import de.randomerror.persistence.DAO.CustomerDAO;
import de.randomerror.util.Injector;
import de.randomerror.util.Provided;

/**
 * Created by henri on 12.06.17.
 */
@Provided
public class ObservableCustomerList extends ObservableDataList<CustomerDTO, CustomerDAO> {
    @Override
    public void onInit() {
        Injector i = Injector.getInstance();
        repo = i.getProvided(CustomerDAO.class);
        super.onInit();
    }
}
