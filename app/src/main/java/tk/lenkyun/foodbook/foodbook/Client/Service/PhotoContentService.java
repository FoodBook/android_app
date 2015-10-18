package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Client.Utils.PhotoContentCache;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoContentService {

    private static PhotoContentService instance = null;
    private static Object lock = new Object();
    PhotoContentCache cache;

    public PhotoContentService(Context context) {
        cache = new PhotoContentCache(context, 30, 60, 20, 100);
    }

    public static void initialize(Context context) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PhotoContentService(context);
                }
            }
        }
    }

    /**
     * Get service instance
     *
     * @return A service instance
     */
    public static PhotoContentService getInstance() {
        if (instance == null) {
            throw new RuntimeException("Please call method PhotoContentService.initialize().");
        }

        return instance;
    }

    public void getPhotoContent(final PhotoItem photoItem, final ContentListener<Bitmap> contentListener){
        Bitmap bmp = cache.get(photoItem);
        if (bmp != null) {
            contentListener.onContentLoaded(new PhotoContent<Bitmap>(bmp));
            return;
        }

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(photoItem.getReferal().toString());
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    PhotoContent<Bitmap> contentPhoto = new PhotoContent<Bitmap>(bitmap);

                    cache.put(photoItem, bitmap);
                    contentListener.onContentLoaded(contentPhoto);
                } catch (MalformedURLException e) {
                    contentListener.onContentFailed("PhotoItem loading error.");
                    Log.e("PhotoContent", "PhotoItem failed open connection.");
                } catch (IOException e) {
                    contentListener.onContentFailed("PhotoItem failed open connection.");
                    Log.e("PhotoContent", "PhotoItem failed open connection.");
                }

                return null;
            }
        }.execute();
    }
}
