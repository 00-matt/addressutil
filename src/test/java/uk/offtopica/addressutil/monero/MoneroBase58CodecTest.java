package uk.offtopica.addressutil.monero;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class MoneroBase58CodecTest {
    private final MoneroBase58Codec base58Codec;

    public MoneroBase58CodecTest() {
        base58Codec = new MoneroBase58Codec();
    }

    @Test
    void testDecodeValidEmpty() throws Exception {
        assertEquals(0, base58Codec.decode("").length);
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @CsvFileSource(resources = "base58-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String expected, String input) throws Exception {
        assertArrayEquals(hexStringToByteArray(expected), base58Codec.decode(input));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "base58-invalid.csv", numLinesToSkip = 1)
    void testDecodeValid(String input) {
        assertThrows(MoneroBase58Exception.class, () -> base58Codec.decode(input));
    }
}
