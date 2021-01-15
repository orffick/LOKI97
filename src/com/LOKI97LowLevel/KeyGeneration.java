package com.LOKI97LowLevel;

public class KeyGeneration implements Calculatable {

    private final int NUM_SUBKEYS = 48;
    private final long DELTA = 0x9E3779B97F4A7C15L;

    public Object makeKey (byte[] k) {

        long[] SK = new long[NUM_SUBKEYS];
        long deltan = DELTA;

        int i = 0;
        long k4, k3, k2, k1;    // составляющие ключа 256-битного ключа => KEY == [k4, k3, k2, k1]
        long f_out;


        k4 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
             (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
             (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
             (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);

        k3 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
             (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
             (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
             (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);

        if (k.length == 16) {   // если ключ 128-бит
            k2 = f(k3, k4);
            k1 = f(k4, k3);
        }
        else {
            k2 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                 (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                 (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                 (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);

            if (k.length == 24) // если ключ 192-бит
                k1 = f(k4, k3);
            else                // стандартно ключ 256-бит
                k1 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                     (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                     (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                     (k[i++] & 0xFFL) <<  8 | (k[i] & 0xFFL);
        }

        // генерируем промежуточные ключи
        for (i = 0; i < NUM_SUBKEYS; i++) {
            f_out = f(k1 + k3 + deltan, k2);
            SK[i] = k4 ^ f_out;
            k4 = k3;
            k3 = k2;
            k2 = k1;
            k1 = SK[i];
            deltan += DELTA;
        }

        return SK;
    }
}
