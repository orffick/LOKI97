package com.LOKI97TopLevel.Modes;

import com.GUI.ProgressChecker;

import java.util.ArrayList;

public interface Modable {
     int blockSize = 16;
     byte[] encrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker);
     byte[] decrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker);

     default byte[] xor(byte[] a, byte[] b)
     {
          byte[] res = new byte[a.length];

          for(int i = 0; i < a.length; i++){
               res[i] = (byte) (a[i]^b[i]);
          }
          return res;
     }
}
