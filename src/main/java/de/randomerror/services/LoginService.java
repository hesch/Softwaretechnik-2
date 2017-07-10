package de.randomerror.services;

import de.randomerror.GUI.exceptions.CredentialsInvalidException;
import de.randomerror.entity.UserDTO;
import de.randomerror.persistence.UserRepo;
import de.randomerror.util.Provided;
import lombok.Getter;

/**
 * Service handling User Login and saving the current User after successful login
 */
@Provided
public class LoginService {
    private UserRepo userRepo;

    @Getter
    private UserDTO currentUser;

    /**
     * Accept login Credentilas and querys the userRepo with the given arguments
     * @param login
     * @param pass
     */
    public void login(String login, String pass) {
        currentUser = userRepo.findByLogin(login)
                        .filter(u -> u.getPassword().equals(pass))
                        .orElseThrow(() -> new CredentialsInvalidException("Benutzername oder Password ist falsch!"));
    }

}
