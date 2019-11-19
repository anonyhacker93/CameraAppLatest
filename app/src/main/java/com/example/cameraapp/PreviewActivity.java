package com.example.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PreviewActivity extends AppCompatActivity {
    private ImageView mPreviewImageView;
    private CameraPhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        photoManager = new CameraPhotoManager();
        setTitle("Preview");
        mPreviewImageView = findViewById(R.id.imagePreview);
        previewPhoto();
    }

    private void previewPhoto() {
        Bitmap bitmap = CameraPhotoManager.getTempBitmap(this);
        if (bitmap != null) {
            mPreviewImageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.save_photo:
                new SaveAsyncTask().execute(((BitmapDrawable) mPreviewImageView.getDrawable()).getBitmap());

                break;
            case R.id.delete_photo:
                if (photoManager.deletePhoto()) {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Unable to deleted", Toast.LENGTH_SHORT).show();
                }


        }

        return true;
    }

    class SaveAsyncTask extends AsyncTask<Bitmap, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            return photoManager.savePhoto(bitmaps[0]);
        }

        @Override
        protected void onPostExecute(Boolean isSaved) {
            String notifyMsg;
            if (isSaved) {
                notifyMsg = getString(R.string.photo_save);
            } else {
                notifyMsg = getString(R.string.photo_not_saved);
            }
         //   sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            new NotificationUtil().createNotification(getBaseContext(), null, notifyMsg, "", R.drawable.ic_info_outline_black_24dp);
        }
    }


}
