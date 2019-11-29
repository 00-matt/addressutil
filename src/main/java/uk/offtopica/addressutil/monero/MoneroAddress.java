package uk.offtopica.addressutil.monero;

import uk.offtopica.addressutil.Address;

import java.util.Optional;

/**
 * Represents a Monero address. May optionally contain a payment id.
 *
 * @see <a href="https://cryptonote.org/cns/cns007.txt" target="_top">CNS007: CryptoNote Keys and Addresses</a>
 */
public class MoneroAddress implements Address {
    private final byte networkByte;
    private final byte[] publicSpendKey;
    private final byte[] publicViewKey;
    private final byte[] paymentId;

    public MoneroAddress(byte networkByte, byte[] publicSpendKey, byte[] publicViewKey, byte[] paymentId) {
        if (publicSpendKey.length != 32) {
            throw new IllegalArgumentException("publicSpendKey must be 32 bytes");
        }
        if (publicViewKey.length != 32) {
            throw new IllegalArgumentException("publicViewKey must be 32 bytes");
        }
        if (paymentId != null && paymentId.length != 8) {
            throw new IllegalArgumentException("paymentId must be 8 bytes if present");
        }

        this.networkByte = networkByte;
        this.publicSpendKey = publicSpendKey;
        this.publicViewKey = publicViewKey;
        this.paymentId = paymentId;
    }

    public MoneroAddress(byte networkByte, byte[] publicSpendKey, byte[] publicViewKey) {
        this(networkByte, publicSpendKey, publicViewKey, null);
    }

    public boolean isIntegratedAddress() {
        return paymentId != null;
    }

    public byte getNetworkByte() {
        return networkByte;
    }

    public byte[] getPublicSpendKey() {
        return publicSpendKey;
    }

    public byte[] getPublicViewKey() {
        return publicViewKey;
    }

    public Optional<byte[]> getPaymentId() {
        return Optional.ofNullable(paymentId);
    }
}
