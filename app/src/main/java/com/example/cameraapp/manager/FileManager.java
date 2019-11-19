package com.example.cameraapp.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class FileManager {

    public static boolean saveBitmap(Bitmap bitmap, String dir, String name) {
        File file = new File(createPath(dir), name);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteBitmap(String dir, String name) {
        File file = new File(createPath(dir), name);
        return file.delete();
    }

    static public class AppDataFileManager {
        public File getFile(Context context, String fileName) {
            File file;
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

    public static void scanChanges(Context context, String dir, String scanFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(createPath(dir) + "/" + scanFileName));
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private static String createPath(String dirName) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        dirPath = dirPath + "/" + dirName;
        return dirPath;
    }
}
