package android.oath.com.flicpic.exception;

public class FlickrException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message
     */
    public FlickrException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message the detail message.
     */
    public FlickrException(String message) {
        super(message);
    }
}
