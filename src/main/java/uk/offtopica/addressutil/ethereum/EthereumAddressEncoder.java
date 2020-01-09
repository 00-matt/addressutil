package uk.offtopica.addressutil.ethereum;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import uk.offtopica.addressutil.AddressEncoder;
import uk.offtopica.addressutil.internal.HexUtils;

import java.nio.charset.StandardCharsets;

public class EthereumAddressEncoder implements AddressEncoder<EthereumAddress> {
    @Override
    public String encode(EthereumAddress address) {
        final String dataHex = HexUtils.byteArrayToHexString(address.getData());
        final String digestHex = getDigestHex(dataHex.toLowerCase());

        final StringBuilder sb = new StringBuilder();

        sb.append("0x");

        for (int i = 0; i < dataHex.length(); i++) {
            final char c = dataHex.charAt(i);
            final boolean upper = Character.digit(digestHex.charAt(i), 16) >= 8;

            if (upper) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }

    private String getDigestHex(String string) {
        Keccak.Digest256 keccak256 = new Keccak.Digest256();
        keccak256.update(string.getBytes(StandardCharsets.US_ASCII));
        final byte[] digestBytes = keccak256.digest();
        return HexUtils.byteArrayToHexString(digestBytes);
    }
}
