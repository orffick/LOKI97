package com.LOKI97TopLevel.Modes;

import com.GUI.ProgressChecker;
import com.LOKI97LowLevel.Decoder;
import com.LOKI97LowLevel.Encoder;

import java.util.ArrayList;

public class ECBMode implements Modable{

    public byte[] encrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker){

        progressChecker.setSize(blocksArray.size());

        byte[] outputBuffer = new byte[blocksArray.size()*blockSize];
        Encoder encoder = new Encoder();

        int step = 0;
        for (byte[] block: blocksArray){
            byte[] temp = encoder.blockEncrypt(block, 0, key);

            System.arraycopy(temp, 0, outputBuffer, (step++)*blockSize, blockSize);
            progressChecker.setValue(step);
        }

        return outputBuffer;
    }

    public byte[] decrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker){

        progressChecker.setSize(blocksArray.size());

        byte[] outputBuffer = new byte[blocksArray.size()*blockSize];
        Decoder decoder = new Decoder();

        int step = 0;
        for (byte[] block: blocksArray){
            byte[] temp = decoder.blockDecrypt(block, 0, key);

            System.arraycopy(temp, 0, outputBuffer, (step++)*blockSize, blockSize);
            progressChecker.setValue(step);
        }

        return outputBuffer;
    }

}
