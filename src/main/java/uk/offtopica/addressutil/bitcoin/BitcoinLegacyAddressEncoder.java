package uk.offtopica.addressutil.bitcoin;

import uk.offtopica.addressutil.AddressEncoder;

public class BitcoinLegacyAddressEncoder implements AddressEncoder<BitcoinLegacyAddress> {
    private final BitcoinBase58CheckCodec codec;

    public BitcoinLegacyAddressEncoder() {
        codec = new BitcoinBase58CheckCodec();
    }

    @Override
    public String encode(BitcoinLegacyAddress address) {
        // concat = network byte || data
        byte[] concat = new byte[1 + address.getData().length];
        concat[0] = address.getNetworkByte();
        System.arraycopy(address.getData(), 0, concat, 1, address.getData().length);
        return codec.encode(concat);
    }
}
