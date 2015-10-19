package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Client.Utils.PhotoContentCache;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoContentService {

    private static PhotoContentService instance = null;
    private static Object lock = new Object();
    PhotoContentCache cache;
    private int mockKey = 0;
    private Map<String, PhotoContent> mockPhotoServer = new LRUMap<>();

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

    public PhotoItem mockAddPhoto(PhotoContent<Bitmap> photoContent) {
        String uriPath = "foodbook://photo/" + mockKey + ".jpg";
        Bitmap content = photoContent.getContent();
        mockPhotoServer.put(uriPath, photoContent);
        return new PhotoItem(Uri.parse(uriPath), content.getWidth(), content.getHeight());
    }

    public void getPhotoContent(final PhotoItem photoItem, final ContentListener<Bitmap> contentListener){
        Bitmap bmp = cache.get(photoItem);
        if (bmp != null) {
            contentListener.onContentLoaded(new PhotoContent<Bitmap>(bmp));
            return;
        }

        // For mock server
        if (mockPhotoServer.get(photoItem) != null) {
            contentListener.onContentLoaded(mockPhotoServer.get(photoItem));
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
