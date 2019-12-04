package uk.offtopica.addressutil.bitcoin;

import java.math.BigInteger;
import java.util.Arrays;

public class BitcoinBase58Codec {
    private static final BigInteger FIFTY_EIGHT = BigInteger.valueOf(58);
    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] MAP = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, -1, -1, -1, -1, -1,
            -1, 9, 10, 11, 12, 13, 14, 15, 16, -1, 17, 18, 19, 20, 21, -1,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1,
            -1, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, -1, 44, 45, 46,
            47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };

    public byte[] decode(String input) throws BitcoinBase58Exception {
        if (input.length() == 0) {
            return new byte[]{};
        }

        byte[] b58 = new byte[input.length()];
        for (int i = 0; i < b58.length; i++) {
            b58[i] = (byte) MAP[input.charAt(i)];
            if (b58[i] == -1) {
                throw new BitcoinBase58Exception("Invalid character");
            }
        }

        int zeroes = 0;
        while (zeroes < b58.length && b58[zeroes] == 0) {
            zeroes++;
        }

        byte[] decoded = new byte[b58.length];
        int out = decoded.length;
        for (int i = zeroes; i < b58.length; ) {
            int r = 0;
            for (int j = i; j < b58.length; j++) {
                int x = r * 58 + b58[j];
                b58[j] = (byte) (x / 256);
                r = x % 256;
            }
            decoded[--out] = (byte) r;

            if (b58[i] == 0) {
                i++;
            }
        }

        int zeroes2 = out;
        while (zeroes2 < decoded.length && decoded[zeroes2] == 0) {
            zeroes2++;
        }

        return Arrays.copyOfRange(decoded, zeroes2 - zeroes, decoded.length);
    }

    public String encode(byte[] input) {
        BigInteger x = new BigInteger(1, input);
        StringBuilder encoded = new StringBuilder();

        int zeroes = 0;
        while (zeroes < input.length && input[zeroes] == 0) {
            zeroes++;
        }

        while (x.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] result = x.divideAndRemainder(FIFTY_EIGHT);
            x = result[0];
            encoded.append(ALPHABET[result[1].intValue()]);
        }

        for (int i = 0; i < zeroes; i++) {
            encoded.append(ALPHABET[0]);
        }

        encoded.reverse();

        return encoded.toString();
    }
}
