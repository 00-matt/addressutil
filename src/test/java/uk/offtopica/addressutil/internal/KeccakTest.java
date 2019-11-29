package uk.offtopica.addressutil.internal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static uk.offtopica.addressutil.HexUtils.hexStringToByteArray;

class KeccakTest {
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "keccak-256.csv", numLinesToSkip = 1)
    void testKeccak256Digest(String expected, String input) {
        if (input == null) {
            input = "";
        }
        Keccak keccak256 = new Keccak(256);
        keccak256.update(hexStringToByteArray(input));
        assertArrayEquals(hexStringToByteArray(expected), keccak256.digestArray());
    }
}
