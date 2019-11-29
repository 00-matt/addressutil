package uk.offtopica.addressutil.internal;

public class ArrayUtils {
    public static int indexOf(char[] a, char key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key)
                return i;
        }
        return -1;
    }

    public static int indexOf(int[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key)
                return i;
        }
        return -1;
    }

    public static <T> int indexOf(T[] a, T key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null && a[i].equals(key) || key == null && a[i] == null)
                return i;
        }
        return -1;
    }
}
