package app.git.exceptions;

public class ServerIsBudyException extends Exception {

    public ServerIsBudyException() {
        super();
    }

    public ServerIsBudyException(String message) {
        super(message);
    }

    public ServerIsBudyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerIsBudyException(Throwable cause) {
        super(cause);
    }
}
