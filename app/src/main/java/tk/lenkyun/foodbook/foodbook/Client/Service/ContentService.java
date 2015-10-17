package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;
import tk.lenkyun.foodbook.foodbook.Data.Photo.ContentPhoto;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class ContentService {
    public void getBitmap(final Photo photo, final ContentListener<Bitmap> contentListener){
        final ContentListener photoListener = contentListener;

        if(photo instanceof ContentPhoto) {
            ContentPhoto<Bitmap> bitmapContentPhoto = null;
            try {
                bitmapContentPhoto = (ContentPhoto<Bitmap>) photo;
            } catch (ClassCastException e) {
            } finally {
                contentListener.onContentLoaded(bitmapContentPhoto);
                return;
            }
        }

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(photo.getReferalImageURI().toString());
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    ContentPhoto<Bitmap> contentPhoto = ContentPhoto.fromPhoto(null, bitmap);
                    photoListener.onContentLoaded(contentPhoto);
                } catch (MalformedURLException e) {
                    photoListener.onContentFailed("Photo loading error.");
                    Log.e("PhotoContent", "Photo failed open connection.");
                } catch (IOException e) {
                    photoListener.onContentFailed("Photo failed open connection.");
                    Log.e("PhotoContent", "Photo failed open connection.");
                }

                return null;
            }
        }.execute();
    }

    private static ContentService instance = null;
    private static Object lock = new Object();

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static ContentService getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new ContentService();
                }
            }
        }

        return instance;
    }
}
