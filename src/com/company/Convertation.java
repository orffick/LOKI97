package com.company;

public class Convertation {

    private static char[] HEX_DIGITS = {
            '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

    public static String byteToString (int n) {
        char[] buf = {
                HEX_DIGITS[(n >>> 4) & 0x0F],
                HEX_DIGITS[ n        & 0x0F]
        };
        return new String(buf);
    }

    public static String shortToString (int n) {
        char[] buf = {
                HEX_DIGITS[(n >>> 12) & 0x0F],
                HEX_DIGITS[(n >>>  8) & 0x0F],
                HEX_DIGITS[(n >>>  4) & 0x0F],
                HEX_DIGITS[ n         & 0x0F]
        };
        return new String(buf);
    }

    public static String longToString(long n) {
        char[] buf = new char[16];
        for (int i = 15; i >= 0; i--) {
            buf[i] = HEX_DIGITS[(int) n & 0x0F];
            n >>>= 4;
        }
        return new String(buf);
    }

    public static String toString (byte[] ba) {
        int length = ba.length;
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[ k        & 0x0F];
        }
        return new String(buf);
    }
}
