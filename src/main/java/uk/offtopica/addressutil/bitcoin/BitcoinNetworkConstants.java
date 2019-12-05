package uk.offtopica.addressutil.bitcoin;

import java.util.Set;

public class BitcoinNetworkConstants {
    public static final BitcoinNetworkConstants MAINNET = new BitcoinNetworkConstants(Set.of((byte) 0x00, (byte) 0x05));

    private final Set<Byte> legacyPrefixes;

    public BitcoinNetworkConstants(Set<Byte> legacyPrefixes) {
        this.legacyPrefixes = legacyPrefixes;
    }

    public boolean containsPrefix(byte b) {
        return legacyPrefixes.contains(b);
    }
}
