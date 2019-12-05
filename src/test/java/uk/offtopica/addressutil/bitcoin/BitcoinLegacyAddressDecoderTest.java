package uk.offtopica.addressutil.bitcoin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class BitcoinLegacyAddressDecoderTest {
    BitcoinLegacyAddressDecoder decoder;

    BitcoinLegacyAddressDecoderTest() {
        decoder = new BitcoinLegacyAddressDecoder();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String addr, byte network, String data) throws Exception {
        BitcoinLegacyAddress address = decoder.decode(addr);
        assertEquals(network, address.getNetworkByte());
        assertArrayEquals(hexStringToByteArray(data), address.getData());
    }
}
