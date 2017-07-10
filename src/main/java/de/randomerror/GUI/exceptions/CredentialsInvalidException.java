package de.randomerror.GUI.exceptions;

/**
 *Exception that is thrown when Invalid Exceptions are entered in the LoginView
 */
public class CredentialsInvalidException extends RuntimeException {
    public CredentialsInvalidException(String message) {
        super(message);
    }
}
