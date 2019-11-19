package com.example.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.example.cameraapp.FileManager.deleteBitmap;

public class CameraPhotoManager {
    private static final String SAVE_DIRECTORY = Environment.DIRECTORY_PICTURES;
    private static final String TEMP_BITMAP_FILE = "TempPic.jpg";

    public static void saveTempBitmap(Context context, Image image) {
        new FileManager.AppDataFileManager().saveFile(context, image, TEMP_BITMAP_FILE);
    }

    public static Bitmap getTempBitmap(Context context) {
        Bitmap bitmap = null;
        File file = new FileManager.AppDataFileManager().getFile(context, TEMP_BITMAP_FILE);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            file.delete();//todo
        }
        return bitmap;
    }

    public boolean savePhoto(Bitmap bitmap) {
        return FileManager.saveBitmap(bitmap, SAVE_DIRECTORY, createPhotoName());
    }

    @NotNull
    private static String createPhotoName() {
        return "Photo_" + Utility.getDateTime() + ".jpg";
    }

    private boolean deletePhoto(String filePath, String name) {
        return deleteBitmap(filePath, name);

    }
}


