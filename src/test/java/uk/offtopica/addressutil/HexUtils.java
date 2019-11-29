package uk.offtopica.addressutil;

public class HexUtils {
    public static byte[] hexStringToByteArray(String hex) {
        byte[] array = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            array[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return array;
    }
}
