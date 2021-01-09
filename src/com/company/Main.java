package com.company;

import com.GUI.ProgressChecker;
import com.GUI.Window;
import com.LOKI97LowLevel.*;
import com.LOKI97TopLevel.LOKI97Cipher;

import javax.swing.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {

        SBoxesGeneration.init();
        PermutationGeneration.init();

        new Window();

//        String defaultPath = System.getProperty("user.dir") + "/resources/";
//
//        byte[] keyX = FileWorker.read(defaultPath+ "keys/128-key");
//        byte[] iv = FileWorker.read(defaultPath + "IV");
//
//        String fileName = "video.mp4";
//        String modeName = "OFB";
//        String fileInput = "/home/orffick/temp/DecEncLOKI97/Input/" + modeName + "/" + fileName;
//        String fileEncrypt = "/home/orffick/temp/DecEncLOKI97/Encrypt/" + modeName + "/" + fileName;
//        String fileDecrypt = "/home/orffick/temp/DecEncLOKI97/Decrypt/" + modeName + "/" + fileName;
//        byte[] input =  FileWorker.read(fileInput);
//        byte[] enc = LOKI97Cipher.encrypt(input, keyX, iv, modeName, new ProgressChecker(new JProgressBar(), new JTable()));
//        FileWorker.write(fileEncrypt, enc);
//
//        byte[] dec = LOKI97Cipher.decrypt(enc, keyX, iv,modeName, new ProgressChecker(new JProgressBar(), new JTable()));
//        FileWorker.write(fileDecrypt, dec);

    }
}
