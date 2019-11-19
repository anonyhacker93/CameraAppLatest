package com.example.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static void saveInApp(Context context, Image image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(context.getExternalFilesDir(null), "pic.jpg");
        } else {
            file = new File(context.getFilesDir(), "pic.jpg");
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

    public static Bitmap fetchInAppBitmap(Context context) {
        File file;
        Bitmap myBitmap = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(context.getExternalFilesDir(null), "pic.jpg");
        } else {
            file = new File(context.getFilesDir(), "pic.jpg");
        }
        if (file.exists()) {

            myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            file.delete();
        }
        return myBitmap;
    }

    public static boolean saveBitmap(Bitmap bitmap) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir = dir + "/" + Environment.DIRECTORY_PICTURES;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        File file = new File(dir, "Photo_" + currentDateandTime+".jpg");
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteFile() {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        return delete(dir);
    }

    private static boolean delete(String filePath) {
        File file = new File(filePath, "Image.jpg");
        file.delete();
        return false;
    }
}
