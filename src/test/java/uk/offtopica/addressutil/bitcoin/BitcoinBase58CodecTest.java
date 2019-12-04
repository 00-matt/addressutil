package uk.offtopica.addressutil.bitcoin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class BitcoinBase58CodecTest {
    BitcoinBase58Codec base58Codec;

    BitcoinBase58CodecTest() {
        base58Codec = new BitcoinBase58Codec();
    }

    @Test
    void testDecodeValidEmpty() throws Exception {
        assertEquals(0, base58Codec.decode("").length);
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @CsvFileSource(resources = "base58-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String decoded, String encoded) throws BitcoinBase58Exception {
        assertArrayEquals(hexStringToByteArray(decoded), base58Codec.decode(encoded));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "base58-invalid.csv", numLinesToSkip = 1)
    void testDecodeInvalid(String encoded) {
        assertThrows(BitcoinBase58Exception.class, () -> base58Codec.decode(encoded));
    }

    @Test
    void testEncodeEmpty() {
        assertEquals("", base58Codec.encode(new byte[]{}));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "base58-valid.csv", numLinesToSkip = 1)
    void testEncode(String decoded, String encoded) {
        assertEquals(encoded, base58Codec.encode(hexStringToByteArray(decoded)));
    }
}
