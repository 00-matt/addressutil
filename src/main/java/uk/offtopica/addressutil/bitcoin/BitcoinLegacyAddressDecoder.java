package uk.offtopica.addressutil.bitcoin;

import uk.offtopica.addressutil.AddressDecoder;
import uk.offtopica.addressutil.InvalidAddressException;

import java.util.Arrays;

public class BitcoinLegacyAddressDecoder implements AddressDecoder<BitcoinLegacyAddress> {
    private final BitcoinNetworkConstants constants;
    private final BitcoinBase58CheckCodec codec;

    public BitcoinLegacyAddressDecoder(BitcoinNetworkConstants constants) {
        codec = new BitcoinBase58CheckCodec();
        this.constants = constants;
    }

    public BitcoinLegacyAddressDecoder() {
        this(BitcoinNetworkConstants.MAINNET);
    }

    @Override
    public BitcoinLegacyAddress decode(String address) throws InvalidAddressException {
        byte[] data;
        try {
            data = codec.decode(address);
        } catch (BitcoinBase58Exception e) {
            throw new InvalidAddressException(e);
        }

        if (!constants.containsPrefix(data[0])) {
            throw new InvalidAddressException("Bad network byte");
        }

        return new BitcoinLegacyAddress(data[0], Arrays.copyOfRange(data, 1, data.length));
    }
}
