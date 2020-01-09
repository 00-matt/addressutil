package uk.offtopica.addressutil.ethereum;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import uk.offtopica.addressutil.AddressDecoder;
import uk.offtopica.addressutil.InvalidAddressException;
import uk.offtopica.addressutil.internal.HexUtils;

import java.nio.charset.StandardCharsets;

public class EthereumAddressDecoder implements AddressDecoder<EthereumAddress> {
    private final EthereumNetworkConstants networkConstants;

    public EthereumAddressDecoder(EthereumNetworkConstants networkConstants) {
        this.networkConstants = networkConstants;
    }

    public EthereumAddressDecoder() {
        this(EthereumNetworkConstants.ETH_MAINNET);
    }

    @Override
    public EthereumAddress decode(String address) throws InvalidAddressException {
        // Strip leading 0x
        if (address.startsWith("0x")) {
            address = address.substring(2);
        }

        // 40 hex chars = 20 bytes
        if (address.length() != 40) {
            throw new InvalidAddressException("Bad address length; must consist of 40 hex chars");
        }

        // Handle EIP-55
        if (isMixedCase(address)) {
            // Digest is of ASCII bytes of hex representation because...
            Keccak.Digest256 keccak256 = new Keccak.Digest256();
            keccak256.update(address.toLowerCase().getBytes(StandardCharsets.US_ASCII));
            final byte[] digestBytes = keccak256.digest();
            final String digestHex = HexUtils.byteArrayToHexString(digestBytes);

            for (int i = 0; i < 40; i++) {
                final char c = address.charAt(i);

                if (!Character.isAlphabetic(c)) {
                    continue;
                }

                final boolean shouldBeUpperCase = Character.digit(digestHex.charAt(i), 16) >= 8;

                if ((shouldBeUpperCase && Character.isLowerCase(c))
                        || (!shouldBeUpperCase && Character.isUpperCase(c))) {
                    throw new InvalidAddressException("Invalid address checksum");
                }
            }
        }

        try {
            return new EthereumAddress(HexUtils.hexStringToByteArray(address));
        } catch (Exception e) {
            throw new InvalidAddressException(e);
        }
    }

    private boolean isMixedCase(String string) {
        return !(string.toLowerCase().equals(string) || string.toUpperCase().equals(string));
    }
}
