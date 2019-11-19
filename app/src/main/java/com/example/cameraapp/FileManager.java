package com.example.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class FileManager {

    public static boolean saveBitmap(Bitmap bitmap, String path, String name) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir = dir + "/" + path;
        File file = new File(dir, name);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteBitmap(String path, String name) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir = dir + "/" + path;
        File file = new File(dir, name);
        return file.delete();
    }

   static public class AppDataFileManager {
        public File getFile(Context context, String fileName) {
            File file;
            Bitmap myBitmap = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                file = new File(context.getExternalFilesDir(null), fileName);
            } else {
                file = new File(context.getFilesDir(), fileName);
            }
            return file;
        }

        public void saveFile(Context context, Image image, String fileName) {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            File file;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                file = new File(context.getExternalFilesDir(null), fileName);
            } else {
                file = new File(context.getFilesDir(), fileName);
            }

            try {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(bytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
