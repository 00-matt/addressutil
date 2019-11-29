package uk.offtopica.addressutil;

/**
 * Thrown to indicate that an address decoder has been passed an invalid or inappropriate address.
 */
public class InvalidAddressException extends Exception {
    /**
     * {@inheritDoc}
     */
    public InvalidAddressException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAddressException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAddressException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
