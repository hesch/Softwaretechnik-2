package de.randomerror.services;

import de.randomerror.GUI.exceptions.CredentialsInvalidException;
import de.randomerror.entity.UserDTO;
import de.randomerror.persistence.UserRepo;
import de.randomerror.util.Provided;
import lombok.Getter;

/**
 * Created by henri on 08.05.17.
 */
@Provided
public class LoginService {
    private UserRepo userRepo;

    @Getter
    private UserDTO currentUser;

    public void login(String login, String pass) {
        currentUser = userRepo.findByLogin(login)
                        .filter(u -> u.getPassword().equals(pass))
                        .orElseThrow(() -> new CredentialsInvalidException("Benutzername oder Password ist falsch!"));
    }

}
