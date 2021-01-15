package com.LOKI97LowLevel;

public class Encoder implements Calculatable {

    public byte[] blockEncrypt (byte[] in, int inOffset, Object sessionKey) {

        long[] SK = (long[]) sessionKey;	// сессионный ключ

        // входной разделяем на две части (левую и правую)
        long L = (in[inOffset++] & 0xFFL) << 56 |
                 (in[inOffset++] & 0xFFL) << 48 |
                 (in[inOffset++] & 0xFFL) << 40 |
                 (in[inOffset++] & 0xFFL) << 32 |
                 (in[inOffset++] & 0xFFL) << 24 |
                 (in[inOffset++] & 0xFFL) << 16 |
                 (in[inOffset++] & 0xFFL) <<  8 |
                 (in[inOffset++] & 0xFFL);

        long R = (in[inOffset++] & 0xFFL) << 56 |
                 (in[inOffset++] & 0xFFL) << 48 |
                 (in[inOffset++] & 0xFFL) << 40 |
                 (in[inOffset++] & 0xFFL) << 32 |
                 (in[inOffset++] & 0xFFL) << 24 |
                 (in[inOffset++] & 0xFFL) << 16 |
                 (in[inOffset++] & 0xFFL) <<  8 |
                 (in[inOffset] & 0xFFL);

        long nR, f_out;
        int k = 0;

        for (int i = 0; i < ROUNDS; i++) { // входной блок проходит 16 раундов
            nR = R + SK[k++];
            f_out = f(nR, SK[k++]);
            nR += SK[k++];
            R = L ^ f_out;
            L = nR;
        }

        // запаковали и отправили зашифрованный блок
        return new byte[]{
                (byte)(R >>> 56), (byte)(R >>> 48),
                (byte)(R >>> 40), (byte)(R >>> 32),
                (byte)(R >>> 24), (byte)(R >>> 16),
                (byte)(R >>>  8), (byte) R,
                (byte)(L >>> 56), (byte)(L >>> 48),
                (byte)(L >>> 40), (byte)(L >>> 32),
                (byte)(L >>> 24), (byte)(L >>> 16),
                (byte)(L >>>  8), (byte) L
        };
    }
}
