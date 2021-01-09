package com.GUI;

import com.company.Convertation;
import com.company.FileWorker;

import java.io.*;
import java.util.Arrays;

public class Configuration
{
    private static String lastPath;

    private String modName ;
    private String cipher;

    private byte[] key;
    private byte[] iv;

    final String keyFileName = "256-key";
    final String ivFileName  = "IV";

    public static String getLastPath(){
        try {
            File file = new File(System.getProperty("user.dir") + "/resources/lastPath.txt");

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            lastPath = reader.readLine();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lastPath;
    }

    public static void setLastPathPath(String lastPath) throws IOException {

        FileWorker.write(System.getProperty("user.dir") + "/resources/lastPath.txt", lastPath.getBytes());
    }


    public byte[] getKey(){
        return key;
    }

    public void setKey(byte[] key){
        this.key = key;
    }

    public byte[] getIV(){
        return iv;
    }

    public void setIV(byte[] iv){
        this.iv = iv;
    }

    public String getCipher(){
        return cipher;
    }

    public void setCipher(String cipher){
        this.cipher = cipher;
    }

    public String getModeName(){
        return modName;
    }

    public void setModName(String modName){
        this.modName = modName;
    }

    public Configuration(){

        key = FileWorker.read(System.getProperty("user.dir") + "/resources/keys/" + keyFileName);
        iv  = FileWorker.read(System.getProperty("user.dir") + "/resources/" + ivFileName);

        cipher = "encrypt";
        modName = "ECB";
    }
}
