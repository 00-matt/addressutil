package uk.offtopica.addressutil.monero;

import uk.offtopica.addressutil.AddressDecoder;
import uk.offtopica.addressutil.InvalidAddressException;
import uk.offtopica.addressutil.internal.Keccak;

import java.util.Arrays;

public class MoneroAddressDecoder implements AddressDecoder<MoneroAddress> {
    private final MoneroBase58Codec base58Codec;
    private final MoneroNetworkConstants networkConstants;

    public MoneroAddressDecoder(MoneroNetworkConstants networkConstants) {
        base58Codec = new MoneroBase58Codec();
        this.networkConstants = networkConstants;
    }

    public MoneroAddressDecoder() {
        this(MoneroNetworkConstants.MAINNET);
    }

    @Override
    public MoneroAddress decode(String address) throws InvalidAddressException {
        byte[] data;
        try {
            data = base58Codec.decode(address);
        } catch (MoneroBase58Exception e) {
            throw new InvalidAddressException(e);
        }

        boolean integrated;
        if (data.length == 77) {
            integrated = true;
        } else if (data.length == 69) {
            integrated = false;
        } else {
            throw new InvalidAddressException("Bad address length");
        }

        byte networkByte = data[0];
        byte[] publicSpendKey = new byte[32];
        byte[] publicViewKey = new byte[32];
        byte[] checksum = new byte[4];
        byte[] paymentId = null;

        System.arraycopy(data, 1, publicSpendKey, 0, 32);
        System.arraycopy(data, 33, publicViewKey, 0, 32);

        if (integrated) {
            paymentId = new byte[8];
            System.arraycopy(data, 65, paymentId, 0, 8);
            System.arraycopy(data, 73, checksum, 0, 4);
        } else {
            System.arraycopy(data, 65, checksum, 0, 4);
        }

        Keccak keccak256 = new Keccak(256);
        keccak256.update(data, 0, data.length - 4); // don't include the checksum in the checksum
        byte[] hash = keccak256.digestArray(4);

        if (!Arrays.equals(hash, checksum)) {
            throw new InvalidAddressException("Bad checksum");
        }

        if (integrated && networkByte != networkConstants.getNetworkByteIntegrated()) {
            throw new InvalidAddressException("Bad network byte");
        }

        if (!integrated && networkByte != networkConstants.getNetworkByteStandard()
                && networkByte != networkConstants.getNetworkByteSubaddress()) {
            throw new InvalidAddressException("Bad network byte");
        }

        return new MoneroAddress(networkByte, publicSpendKey, publicViewKey, paymentId);
    }
}
