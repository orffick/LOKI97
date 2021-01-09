package com.LOKI97TopLevel.Modes;

import com.GUI.ProgressChecker;
import com.LOKI97LowLevel.Decoder;
import com.LOKI97LowLevel.Encoder;

import java.util.ArrayList;

public class CBCMode implements Modable{
    public byte[] encrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker){

        progressChecker.setSize(blocksArray.size());

        byte[] outputBuffer = new byte[blocksArray.size()*blockSize];
        Encoder encoder = new Encoder();

        int step = 0;
        byte[] encBlock = iv;

        for (byte[] block: blocksArray)
        {
            byte[] temp = xor(encBlock, block);

            encBlock = encoder.blockEncrypt(temp, 0, key);

            System.arraycopy(encBlock, 0, outputBuffer, (step++)*blockSize, blockSize);
            progressChecker.setValue(step);
        }

        return outputBuffer;
    }

    public byte[] decrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker){

        progressChecker.setSize(blocksArray.size());

        byte[] outputBuffer = new byte[blocksArray.size()*blockSize];
        Decoder decoder = new Decoder();

        int step = 0;
        byte[] decBlock;
        byte[] encBlock = iv;

        for (byte[] block: blocksArray)
        {
            decBlock = decoder.blockDecrypt(block, 0, key);

            byte[] temp = xor (decBlock, encBlock);
            encBlock = block; // !

            System.arraycopy(temp, 0, outputBuffer, (step++)*blockSize, blockSize);
            progressChecker.setValue(step);
        }

        return outputBuffer;
    }
}
