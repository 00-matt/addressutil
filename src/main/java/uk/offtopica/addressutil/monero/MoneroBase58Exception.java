package uk.offtopica.addressutil.monero;

public class MoneroBase58Exception extends Exception {
    public MoneroBase58Exception() {
    }

    public MoneroBase58Exception(String message) {
        super(message);
    }

    public MoneroBase58Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public MoneroBase58Exception(Throwable cause) {
        super(cause);
    }
}
