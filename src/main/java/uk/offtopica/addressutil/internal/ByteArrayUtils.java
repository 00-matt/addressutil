package uk.offtopica.addressutil.internal;

public class ByteArrayUtils {
    public static byte[] longToByteArray(long val, int size) {
        byte[] result = new byte[size];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = (byte) (val & 0xFF);
            val >>= 8;
        }
        return result;
    }

    public static byte[] longToByteArray(long val) {
        return longToByteArray(val, Long.BYTES);
    }
}
