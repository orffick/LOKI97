package com.LOKI97TopLevel.Modes;

import com.GUI.ProgressChecker;
import com.LOKI97LowLevel.Encoder;

import java.util.ArrayList;

public class OFBMode implements Modable {
    public byte[] encrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker){

        progressChecker.setSize(blocksArray.size());

        byte[] outputBuffer = new byte[blocksArray.size()*blockSize];
        Encoder encoder = new Encoder();

        int step = 0;
        byte[] encBlock = iv;

        for (byte[] block: blocksArray)
        {
            encBlock = encoder.blockEncrypt(encBlock, 0, key);
            byte res[]  = xor(encBlock, block);

            System.arraycopy(res, 0, outputBuffer, (step++)*blockSize, blockSize);
            progressChecker.setValue(step);
        }

        return outputBuffer;
    }

    public byte[] decrypt(ArrayList<byte[]> blocksArray, Object key, byte[] iv, ProgressChecker progressChecker)
    {
        return encrypt(blocksArray, key, iv, progressChecker);
    }
}
