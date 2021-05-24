package com.example.countryapp.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public LoadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }


    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlImg = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urlImg).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
