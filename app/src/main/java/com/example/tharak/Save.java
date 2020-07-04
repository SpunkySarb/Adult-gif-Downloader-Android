package com.example.tharak;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Save implements Runnable {
public static String dLink;
    public Save(String URL){


dLink= URL;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {

String File_NAME = String.format("%d.gif", System.currentTimeMillis());
        File Directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
        Directory.mkdir();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download" +"/"+ File_NAME;
        try (BufferedInputStream in = new BufferedInputStream(new URL(dLink).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);

            }
        } catch (IOException e) {
            // handle exception
          //  error = e.getMessage();
          //  System.out.println(e.getMessage());
        }







    }
}
