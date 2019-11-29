package uk.offtopica.addressutil.monero;

public class MoneroNetworkConstants {
    public static final MoneroNetworkConstants MAINNET = new MoneroNetworkConstants((byte) 0x12, (byte) 0x2a, (byte) 0x13);

    private final byte networkByteStandard;
    private final byte networkByteSubaddress;
    private final byte networkByteIntegrated;

    public MoneroNetworkConstants(byte networkByteStandard, byte networkByteSubaddress, byte networkByteIntegrated) {
        this.networkByteStandard = networkByteStandard;
        this.networkByteSubaddress = networkByteSubaddress;
        this.networkByteIntegrated = networkByteIntegrated;
    }

    public byte getNetworkByteStandard() {
        return networkByteStandard;
    }

    public byte getNetworkByteSubaddress() {
        return networkByteSubaddress;
    }

    public byte getNetworkByteIntegrated() {
        return networkByteIntegrated;
    }
}
