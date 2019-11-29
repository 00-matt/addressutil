package uk.offtopica.addressutil.monero;

import uk.offtopica.addressutil.internal.ArrayUtils;
import uk.offtopica.addressutil.internal.ByteArrayUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * Encoder and decoder for Base58 strings.
 * Monero uses a custom variant of Base58 that works on 8 byte blocks.
 */
public class MoneroBase58Codec {
    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] ENCODED_BLOCK_SIZES = {0, 2, 3, 5, 6, 7, 9, 10, 11};
    private static final int FULL_BLOCK_SIZE = 8;
    private static final int FULL_ENCODED_BLOCK_SIZE = ENCODED_BLOCK_SIZES[FULL_BLOCK_SIZE];
    private static final BigInteger TWOPOW64 = BigInteger.TWO.pow(64);
    private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf(ALPHABET.length);

    public byte[] decode(String base58) throws MoneroBase58Exception {
        byte[] encoded = base58.getBytes(Charset.defaultCharset());
        if (encoded.length == 0) {
            return new byte[]{};
        }

        int numFullBlocks = (int) Math.floor((double) encoded.length / (double) FULL_ENCODED_BLOCK_SIZE);
        int finalBlockSize = encoded.length % FULL_ENCODED_BLOCK_SIZE;
        int finalDecodedBlockSize = ArrayUtils.indexOf(ENCODED_BLOCK_SIZES, finalBlockSize);
        if (finalDecodedBlockSize < 0) {
            throw new MoneroBase58Exception("Invalid encoding length");
        }
        int dataSize = numFullBlocks * FULL_BLOCK_SIZE + finalDecodedBlockSize;
        byte[] data = new byte[dataSize];
        for (int i = 0; i < numFullBlocks; i++) {
            byte[] block = new byte[FULL_ENCODED_BLOCK_SIZE];
            System.arraycopy(encoded, i * FULL_ENCODED_BLOCK_SIZE, block, 0, FULL_ENCODED_BLOCK_SIZE);
            decodeBlock(block, data, i * FULL_BLOCK_SIZE);
        }
        if (finalBlockSize > 0) {
            byte[] block = new byte[finalBlockSize];
            System.arraycopy(encoded, numFullBlocks * FULL_ENCODED_BLOCK_SIZE, block, 0, finalBlockSize);
            decodeBlock(block, data, numFullBlocks * FULL_BLOCK_SIZE);
        }

        return data;
    }

    private void decodeBlock(byte[] data, byte[] buf, int idx) throws MoneroBase58Exception {
        if (data.length == 0 || data.length > FULL_ENCODED_BLOCK_SIZE) {
            throw new MoneroBase58Exception("Invalid block length");
        }
        int resultSize = ArrayUtils.indexOf(ENCODED_BLOCK_SIZES, data.length);
        if (resultSize <= 0) {
            throw new MoneroBase58Exception("Invalid block size");
        }

        BigInteger result = BigInteger.ZERO;
        BigInteger order = BigInteger.ONE;

        for (int i = data.length - 1; i >= 0; i--) {
            int digit = ArrayUtils.indexOf(ALPHABET, (char) data[i]);
            if (digit < 0) {
                throw new MoneroBase58Exception("Invalid character");
            }
            BigInteger product = order.multiply(BigInteger.valueOf(digit)).add(result);
            if (product.compareTo(TWOPOW64) == 1) {
                throw new MoneroBase58Exception("Overflow");
            }
            result = product;
            order = order.multiply(ALPHABET_SIZE);
        }

        byte[] val = ByteArrayUtils.longToByteArray(result.longValue(), resultSize);
        System.arraycopy(val, 0, buf, idx, resultSize);
    }

    public String encode(byte[] data) {
        // TODO: Not yet implemented.
        throw new UnsupportedOperationException();
    }
}
