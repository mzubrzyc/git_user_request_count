package app.git.exceptions;

public class ServerIsBusyException extends Exception {

    public ServerIsBusyException() {
        super();
    }

    public ServerIsBusyException(String message) {
        super(message);
    }

    public ServerIsBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerIsBusyException(Throwable cause) {
        super(cause);
    }
}
