package com.example.cameraapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.nio.ByteBuffer;

public class ImageUtil {
    public static Bitmap decodeToBitmap(final Image img) {
        ByteBuffer buffer = img.getPlanes()[0].getBuffer();
        byte[] imageBytes = new byte[buffer.remaining()];
        buffer.get(imageBytes);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}
