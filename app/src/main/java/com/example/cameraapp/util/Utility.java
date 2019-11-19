package com.example.cameraapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
