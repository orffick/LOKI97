package com.LOKI97LowLevel;


import com.company.Convertation;

public class KeyGeneration implements Calculatable {

    private int NUM_SUBKEYS = 48;
    private long DELTA = 0x9E3779B97F4A7C15L;

    public Object makeKey (byte[] k) {

        long[] SK = new long[NUM_SUBKEYS];	// array of subkeys

        long deltan = DELTA;			// multiples of delta

        int i = 0;				// index into key input
        long k4, k3, k2, k1;			// key schedule 128-bit entities
        long f_out;				// fn f output value for debug

        // pack key into 128-bit entities: k4, k3, k2, k1
        k4 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);
        k3 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);

        if (k.length == 16) {   // 128-bit key - call fn f twice to gen 256 bits
            k2 = f(k3, k4);
            k1 = f(k4, k3);
        } else {                // 192 or 256-bit key - pack k2 from key data
            k2 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                    (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                    (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                    (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);
            if (k.length == 24) // 192-bit key - call fn f once to gen 256 bits
                k1 = f(k4, k3);
            else                // 256-bit key - pack k1 from key data
                k1 = (k[i++] & 0xFFL) << 56 | (k[i++] & 0xFFL) << 48 |
                        (k[i++] & 0xFFL) << 40 | (k[i++] & 0xFFL) << 32 |
                        (k[i++] & 0xFFL) << 24 | (k[i++] & 0xFFL) << 16 |
                        (k[i++] & 0xFFL) <<  8 | (k[i++] & 0xFFL);
        }

        // iterate over all LOKI97 rounds to generate the required subkeys
        for (i = 0; i < NUM_SUBKEYS; i++) {
            f_out = f(k1 + k3 + deltan, k2);
            SK[i] = k4 ^ f_out;		// compute next subkey value using fn f
            k4 = k3;			// exchange the other words around
            k3 = k2;
            k2 = k1;
            k1 = SK[i];
            deltan += DELTA;		// next multiple of delta
        }

        return SK;
    }

}
