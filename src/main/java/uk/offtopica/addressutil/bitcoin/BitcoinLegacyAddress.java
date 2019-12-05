package uk.offtopica.addressutil.bitcoin;

import java.util.Arrays;
import java.util.Objects;

public class BitcoinLegacyAddress implements BitcoinAddress {
    private final byte networkByte;
    private final byte[] data;

    public BitcoinLegacyAddress(byte networkByte, byte[] data) {
        this.networkByte = networkByte;
        this.data = data;
    }

    public byte getNetworkByte() {
        return networkByte;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitcoinLegacyAddress that = (BitcoinLegacyAddress) o;
        return networkByte == that.networkByte &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(networkByte);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
