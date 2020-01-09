package uk.offtopica.addressutil.ethereum;

import uk.offtopica.addressutil.Address;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class EthereumAddress implements Address {
    private final Integer chainId;
    private final byte[] data;

    public EthereumAddress(Integer chainId, byte[] data) {
        if (data.length != 20) {
            throw new IllegalArgumentException("data must consist of 20 bytes");
        }

        this.chainId = chainId;
        this.data = data;
    }

    public EthereumAddress(byte[] data) {
        this(null, data);
    }

    public Optional<Integer> getChainId() {
        return Optional.ofNullable(chainId);
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EthereumAddress that = (EthereumAddress) o;
        return Objects.equals(chainId, that.chainId) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(chainId);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
