package com.example.cameraapp.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cameraapp.R;
import com.example.cameraapp.manager.CameraPhotoManager;
import com.example.cameraapp.util.NotificationBuilder;

public class PreviewActivity extends AppCompatActivity {
    private ImageView mPreviewImageView;
    private CameraPhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setTitle(getString(R.string.previewTitle));

        photoManager = new CameraPhotoManager();
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
                    Toast.makeText(this, this.getString(R.string.photo_delete), Toast.LENGTH_SHORT).show();
                }
                finish();
        }

        return true;
    }

    class SaveAsyncTask extends AsyncTask<Bitmap, Void, Boolean> {
        private NotificationBuilder notificationBuilder;

        @Override
        protected void onPreExecute() {
            notificationBuilder = new NotificationBuilder();
        }

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            publishProgress(null);
            return photoManager.savePhoto(bitmaps[0]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            notificationBuilder.createNotification(PreviewActivity.this, null, getString(R.string.photo_saving), "", R.drawable.ic_info_outline_black_24dp);
        }

        @Override
        protected void onPostExecute(Boolean isSaved) {
            String notifyMsg;
            if (isSaved) {
                notifyMsg = getString(R.string.photo_save);
                photoManager.photoScanChange(PreviewActivity.this);
            } else {
                notifyMsg = getString(R.string.photo_not_saved);
            }
            notificationBuilder.createNotification(PreviewActivity.this, null, notifyMsg, "", R.drawable.ic_info_outline_black_24dp);
        }
    }


}
