package kapsalon.nl.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
