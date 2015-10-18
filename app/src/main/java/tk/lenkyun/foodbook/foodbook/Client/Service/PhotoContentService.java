package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoContentService {

    private static PhotoContentService instance = null;
    private static Object lock = new Object();

    /**
     * Get service instance if not exists
     *
     * @return A service instance
     */
    public static PhotoContentService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PhotoContentService();
                }
            }
        }

        return instance;
    }

    public void getPhotoContent(final PhotoItem photoItem, final ContentListener<Bitmap> contentListener){

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(photoItem.getReferal().toString());
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    PhotoContent<Bitmap> contentPhoto = PhotoContent.fromPhoto(null, bitmap);
                    photoListener.onContentLoaded(contentPhoto);
                } catch (MalformedURLException e) {
                    photoListener.onContentFailed("PhotoItem loading error.");
                    Log.e("PhotoContent", "PhotoItem failed open connection.");
                } catch (IOException e) {
                    photoListener.onContentFailed("PhotoItem failed open connection.");
                    Log.e("PhotoContent", "PhotoItem failed open connection.");
                }

                return null;
            }
        }.execute();
    }
}
