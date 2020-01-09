package uk.offtopica.addressutil.bitcoin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.offtopica.addressutil.internal.HexUtils.hexStringToByteArray;

class BitcoinLegacyAddressEncoderTest {
    BitcoinLegacyAddressEncoder encoder;

    BitcoinLegacyAddressEncoderTest() {
        encoder = new BitcoinLegacyAddressEncoder();
    }

    @ParameterizedTest(name = "[{index}] {1}:{2}")
    @CsvFileSource(resources = "address-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String addr, byte network, String data) throws Exception {
        BitcoinLegacyAddress address = new BitcoinLegacyAddress(network, hexStringToByteArray(data));
        String encoded = encoder.encode(address);
        assertEquals(addr, encoded);
    }
}
