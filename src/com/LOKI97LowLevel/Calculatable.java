package com.LOKI97LowLevel;

public interface Calculatable {

    int ROUNDS = 16;
    int NUM_SUBKEYS = 48;

    default long f (long A, long B) {
        byte[] S1 = SBoxesGeneration.getS1Box();
        byte[] S2 = SBoxesGeneration.getS2Box();
        long[] P  = PermutationGeneration.getPermutation();

        int Al = (int)(A >>> 32);
        int Ar = (int) A;
        int Br = (int) B;
        long d = ((long)((Al & ~Br) | (Ar & Br)) << 32) |
                 ((long)((Ar & ~Br) | (Al & Br)) & 0xFFFFFFFFL);


        long e = P[S1[(int)((d >>> 56 | d << 8) & 0x1FFF)] & 0xFF] >>> 7 |
                 P[S2[(int)((d >>> 48)          &  0x7FF)] & 0xFF] >>> 6 |
                 P[S1[(int)((d >>> 40)          & 0x1FFF)] & 0xFF] >>> 5 |
                 P[S2[(int)((d >>> 32)          &  0x7FF)] & 0xFF] >>> 4 |
                 P[S2[(int)((d >>> 24)          &  0x7FF)] & 0xFF] >>> 3 |
                 P[S1[(int)((d >>> 16)          & 0x1FFF)] & 0xFF] >>> 2 |
                 P[S2[(int)((d >>>  8)          &  0x7FF)] & 0xFF] >>> 1 |
                 P[S1[(int)( d                  & 0x1FFF)] & 0xFF];

        long f = (S2[(int)(((e>>>56) & 0xFF) | ((B>>>53) &  0x700))] & 0xFFL) << 56 |
                 (S2[(int)(((e>>>48) & 0xFF) | ((B>>>50) &  0x700))] & 0xFFL) << 48 |
                 (S1[(int)(((e>>>40) & 0xFF) | ((B>>>45) & 0x1F00))] & 0xFFL) << 40 |
                 (S1[(int)(((e>>>32) & 0xFF) | ((B>>>40) & 0x1F00))] & 0xFFL) << 32 |
                 (S2[(int)(((e>>>24) & 0xFF) | ((B>>>37) &  0x700))] & 0xFFL) << 24 |
                 (S2[(int)(((e>>>16) & 0xFF) | ((B>>>34) &  0x700))] & 0xFFL) << 16 |
                 (S1[(int)(((e>>> 8) & 0xFF) | ((B>>>29) & 0x1F00))] & 0xFFL) <<  8 |
                 (S1[(int)(( e       & 0xFF) | ((B>>>24) & 0x1F00))] & 0xFFL);

        return f;
    }
}
