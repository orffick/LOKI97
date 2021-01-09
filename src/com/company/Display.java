package com.company;

public class Display {

    public static void sBox(byte[] S){
        for(int i=0; i <S.length/16;i++) {
            System.out.print(Convertation.shortToString(i)+": ");
            for(int j=0;j<16;j++) System.out.print(Convertation.byteToString(S[i*16+j])+" ");
            System.out.println();}
    }

    public static void permutation(long[] P){
        for(int i=0;i<256;i++)
            System.out.println(Convertation.byteToString(i)+": "+Convertation.longToString(P[i]));
    }
}
