package uk.offtopica.addressutil.bitcoin;

public class BitcoinBase58Exception extends Exception {
    public BitcoinBase58Exception() {
    }

    public BitcoinBase58Exception(String message) {
        super(message);
    }

    public BitcoinBase58Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public BitcoinBase58Exception(Throwable cause) {
        super(cause);
    }
}
