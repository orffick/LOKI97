package com.LOKI97LowLevel;

public class SBoxesGeneration {

    //S1-box
    private final static int S1_GEN = 0x2911;
    private final static int S1_SIZE = 0x2000;
    private final static byte[] S1 = new byte[S1_SIZE];

    //S2-box
    private final static int S2_GEN = 0xAA7;
    private final static int S2_SIZE = 0x800;
    private final static byte[] S2 = new byte[S2_SIZE];

    private static void generationS1Box(){
        int S1_MASK = S1_SIZE - 1;

        for (int i = 0; i < S1_SIZE; i++) {
            int b = i ^ S1_MASK;
            S1[i] = exp3(b, S1_GEN, S1_SIZE);
        }
    }

    private static void generationS2Box(){
        int S2_MASK = S2_SIZE - 1;

        for (int i = 0; i < S2_SIZE; i++) {
            int b = i ^ S2_MASK;
            S2[i] = exp3(b, S2_GEN, S2_SIZE);
        }
    }

    private static byte exp3 (int b, int g, int n) {
        if (b == 0)
            return 0;

        int r = b;            // r = b ** 1
        b = mult(r, b, g, n); // r = b ** 2
        r = mult(r, b, g, n); // r = b ** 3
        return (byte) r;
    }

    private static int mult (int a, int b, int g, int n) {
        int p = 0;
        while (b != 0) {
            if ((b & 0x01) != 0)
                p ^= a;
            a <<= 1;
            if (a >= n)
                a ^= g;
            b >>>= 1;
        }
        return p;
    }

    public static byte[] getS1Box() { return S1;}
    public static byte[] getS2Box() { return S2;}

    public static void init(){
        generationS1Box();
        generationS2Box();
    }
}
