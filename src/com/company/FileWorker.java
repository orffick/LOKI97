package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorker {

    public static byte[] read(String fileName){
        byte[] buffer = {};

        try(FileInputStream fin = new FileInputStream(fileName)) {
            buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        return buffer;
    }

    public static void write(String fileName, byte[] bytes){
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(bytes, 0, bytes.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
