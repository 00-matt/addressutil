package uk.offtopica.addressutil.ethereum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import uk.offtopica.addressutil.internal.HexUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EthereumAddressEncoderTest {
    EthereumAddressEncoder encoder;

    EthereumAddressEncoderTest() {
        encoder = new EthereumAddressEncoder();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-encode-valid-eth-mainnet.csv", numLinesToSkip = 1)
    void testEncode(String expected, String dataHex) {
        final EthereumAddress address = new EthereumAddress(HexUtils.hexStringToByteArray(dataHex));
        assertEquals(expected, encoder.encode(address));
    }
}
