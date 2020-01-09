package uk.offtopica.addressutil.monero;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.offtopica.addressutil.internal.HexUtils.hexStringToByteArray;

class MoneroAddressEncoderTest {
    private final MoneroAddressEncoder encoder;
    private final MoneroNetworkConstants network;

    MoneroAddressEncoderTest() {
        encoder = new MoneroAddressEncoder();
        network = MoneroNetworkConstants.MAINNET;
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid.csv", numLinesToSkip = 1)
    void testEncode(String addr, String publicSpendKey, String publicViewKey) throws Exception {
        MoneroAddress address = new MoneroAddress(
                addr.startsWith("4") ? network.getNetworkByteStandard() : network.getNetworkByteSubaddress(),
                hexStringToByteArray(publicSpendKey),
                hexStringToByteArray(publicViewKey));

        String actual = encoder.encode(address);

        assertEquals(addr, actual);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid-integrated.csv", numLinesToSkip = 1)
    void testEncodeIntegrated(String addr, String publicSpendKey, String publicViewKey, String paymentId) throws Exception {
        MoneroAddress address = new MoneroAddress(network.getNetworkByteIntegrated(),
                hexStringToByteArray(publicSpendKey), hexStringToByteArray(publicViewKey),
                hexStringToByteArray(paymentId));

        String actual = encoder.encode(address);

        assertEquals(addr, actual);
    }
}
