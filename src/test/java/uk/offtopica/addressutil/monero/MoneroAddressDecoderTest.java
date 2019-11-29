package uk.offtopica.addressutil.monero;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class MoneroAddressDecoderTest {
    private final MoneroAddressDecoder decoder;

    MoneroAddressDecoderTest() {
        decoder = new MoneroAddressDecoder();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String addr, String publicSpendKey, String publicViewKey) throws Exception {
        MoneroAddress address = decoder.decode(addr);
        assertArrayEquals(hexStringToByteArray(publicSpendKey), address.getPublicSpendKey());
        assertArrayEquals(hexStringToByteArray(publicViewKey), address.getPublicViewKey());
        assertFalse(address.getPaymentId().isPresent());
        assertFalse(address.isIntegratedAddress());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid-integrated.csv", numLinesToSkip = 1)
    void testDecodeValidIntegrated(String addr, String publicSpendKey, String publicViewKey, String paymentId) throws Exception {
        MoneroAddress address = decoder.decode(addr);
        assertArrayEquals(hexStringToByteArray(publicSpendKey), address.getPublicSpendKey());
        assertArrayEquals(hexStringToByteArray(publicViewKey), address.getPublicViewKey());
        assertArrayEquals(hexStringToByteArray(paymentId), address.getPaymentId().orElseThrow());
        assertTrue(address.isIntegratedAddress());
    }
}
