package uk.offtopica.addressutil.bitcoin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class BitcoinBase58CheckCodecTest {
    BitcoinBase58CheckCodec base58CheckCodec;

    BitcoinBase58CheckCodecTest() {
        base58CheckCodec = new BitcoinBase58CheckCodec();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "base58check-invalid.csv", numLinesToSkip = 1)
    void testDecodeInvalid(String encoded) {
        assertThrows(BitcoinBase58Exception.class, () -> base58CheckCodec.decode(encoded));
    }

    @Test
    void testDecodeValidEmpty() throws Exception {
        assertEquals(0, base58CheckCodec.decode("3QJmnh").length);
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @CsvFileSource(resources = "base58check-valid.csv", numLinesToSkip = 1)
    void testDecodeValid(String decoded, String encoded) throws BitcoinBase58Exception {
        assertArrayEquals(hexStringToByteArray(decoded), base58CheckCodec.decode(encoded));
    }

    @Test
    void testEncodeEmpty() {
        assertEquals("3QJmnh", base58CheckCodec.encode(new byte[]{}));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "base58check-valid.csv", numLinesToSkip = 1)
    void testEncode(String decoded, String encoded) {
        assertEquals(encoded, base58CheckCodec.encode(hexStringToByteArray(decoded)));
    }
}
