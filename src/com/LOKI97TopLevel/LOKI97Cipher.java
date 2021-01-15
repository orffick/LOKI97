package com.LOKI97TopLevel;

import com.GUI.ProgressChecker;
import com.LOKI97LowLevel.KeyGeneration;
import com.LOKI97TopLevel.Modes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LOKI97Cipher {

    final static int blockSize = 16;
    final static byte specialByte = '@';

    public static byte[] encrypt(byte[] inputBuffer, byte[] keyX, byte[] iv, String modeName, ProgressChecker progressChecker) {

        KeyGeneration keyGeneration = new KeyGeneration();
        int blocks;
        ArrayList<byte[]> blocksArray;

        if((inputBuffer.length % blockSize) == 0)
            blocks = inputBuffer.length / 16;
        else
            blocks = inputBuffer.length/16 + 1;

        blocksArray = new ArrayList<>(blocks);

        // чтобы не заполнять последний блок нулями(потому что они бывают значащими) -> "blocks - 1"
        int lastStep = 0;
        for (int i = 0; i < blocks - 1; i++){
            int step = i*blockSize;
            lastStep = blockSize + step;

            blocksArray.add(Arrays.copyOfRange(inputBuffer, step, lastStep));
        }

        byte[] lastBlock = new byte[blockSize];
        for(int i = 0; i < blockSize; i++){

            int tempStep = i + lastStep;

            if((inputBuffer.length - tempStep) <= 0)
                lastBlock[i] = specialByte;
            else
                lastBlock[i] = inputBuffer[tempStep];
        }

        blocksArray.add(lastBlock);

        Object key = keyGeneration.makeKey(keyX);
        Modable mode = null;

        switch (modeName)
        {
            case "ECB": mode = new ECBMode();
                break;
            case "CBC": mode = new CBCMode();
                break;
            case "CFB": mode = new CFBMode();
                break;
            case "OFB": mode = new OFBMode();
                break;
            default:
                break;
        }

        byte[] outputBuffer = mode.encrypt(blocksArray, key, iv, progressChecker);

        return Arrays.copyOfRange(outputBuffer, 0,  outputBuffer.length );
    }

    public static byte[] decrypt(byte[] inputBuffer, byte[] keyX, byte[] iv, String modeName, ProgressChecker progressChecker) {

        KeyGeneration keyGeneration = new KeyGeneration();
        int blocks; // количество блоков
        int badCharCount = 0;
        ArrayList<byte[]> blocksArray;

        if((inputBuffer.length % blockSize) == 0)
            blocks = inputBuffer.length / 16;
        else
            blocks = inputBuffer.length/16 + 1;

        blocksArray = new ArrayList<>(blocks);

        for (int i = 0; i < blocks; i++){
            int step = i*blockSize;
            blocksArray.add(Arrays.copyOfRange(inputBuffer, step, blockSize + step));
        }

        Object key = keyGeneration.makeKey(keyX);
        Modable mode = null;

        switch (modeName)
        {
            case "ECB": mode = new ECBMode();
                break;
            case "CBC": mode = new CBCMode();
                break;
            case "CFB": mode = new CFBMode();
                break;
            case "OFB": mode = new OFBMode();
                break;
            default:
                break;
        }

        byte[] outputBuffer = mode.decrypt(blocksArray, key, iv, progressChecker);

        for(int i = 0; i < 15; i++){
            if (outputBuffer[outputBuffer.length - i - 1] == specialByte)
                badCharCount++;
            else
                break;
        }

        return Arrays.copyOfRange(outputBuffer, 0, outputBuffer.length - badCharCount);
    }
}
