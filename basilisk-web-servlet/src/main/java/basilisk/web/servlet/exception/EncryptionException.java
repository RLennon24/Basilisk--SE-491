package basilisk.web.servlet.exception;

public class EncryptionException extends RuntimeException {

    public EncryptionException() {
    }

    public EncryptionException(String message) {
        super(message);
        System.err.println(message);
    }

    public EncryptionException(Throwable cause) {
        super(cause);
        System.err.println(cause);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
        System.err.println(message);
        System.err.println(cause);
    }

}
