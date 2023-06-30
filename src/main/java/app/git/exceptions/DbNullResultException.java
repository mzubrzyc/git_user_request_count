package app.git.exceptions;

public class DbNullResultException extends Exception {

    public DbNullResultException() {
        super();
    }

    public DbNullResultException(String message) {
        super(message);
    }

    public DbNullResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbNullResultException(Throwable cause) {
        super(cause);
    }
}
