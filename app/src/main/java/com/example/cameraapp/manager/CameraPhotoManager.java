package com.example.cameraapp.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;

import com.example.cameraapp.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.example.cameraapp.manager.FileManager.deleteBitmap;

public class CameraPhotoManager {
    private static final String SAVE_DIRECTORY = Environment.DIRECTORY_PICTURES;
    private static final String TEMP_BITMAP_FILE = "TempPic.jpg";
    private static final String PHOTO_PREFIX = "Photo_";
    private static final String PHOTO_EXTENSION = ".jpg";
    private String currentPhotoName = "";

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
        currentPhotoName = createPhotoName();
        return FileManager.saveBitmap(bitmap, SAVE_DIRECTORY, currentPhotoName);
    }

    @NotNull
    private static String createPhotoName() {
        return PHOTO_PREFIX + Utility.getDateTime() + PHOTO_EXTENSION;
    }

    public void photoScanChange(Context context) {
        FileManager.scanChanges(context, SAVE_DIRECTORY, currentPhotoName);
    }


    public boolean deletePhoto() {
        return deleteBitmap(SAVE_DIRECTORY, currentPhotoName);
    }
}


