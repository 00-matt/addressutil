package uk.offtopica.addressutil.ethereum;

import uk.offtopica.addressutil.Address;

import java.util.Arrays;

public class EthereumAddress implements Address {
    private final byte[] data;

    public EthereumAddress(byte[] data) {
        if (data.length != 20) {
            throw new IllegalArgumentException("data must consist of 20 bytes");
        }

        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EthereumAddress that = (EthereumAddress) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
