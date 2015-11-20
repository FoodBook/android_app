package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.apache.commons.collections4.map.LRUMap;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import tk.lenkyun.foodbook.foodbook.Client.Utils.PhotoContentCache;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoContentService {

    private static PhotoContentService instance = null;
    private static Object lock = new Object();
    PhotoContentCache cache;
    private int mockKey = 0;
    private Context context;

    public PhotoContentService(Context context) {
        cache = new PhotoContentCache(context, 5, 120, 5, 30);
        this.context = context;
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

    public PhotoItem mockAddPhoto(PhotoContent photoContent) {
        String uriPath = "foodbook://photo/" + mockKey++ + ".jpg";
        Bitmap content = photoContent.getContent();
        return new PhotoItem(Uri.parse(uriPath), content.getWidth(), content.getHeight());
    }

    public void getPhotoContent(final PhotoItem photoItem, ImageView imageView){
        if(photoItem.getReferal() == null || imageView == null)
            return;

        Glide.with(context)
                .load(photoItem.getReferal())
                .centerCrop()
                .into(imageView);
    }

    public Promise<Bitmap> getPhotoContent(final PhotoItem photoItem){
        return getPhotoContent(photoItem, 800, 800);
    }

    public Promise<Bitmap> getPhotoContent(final PhotoItem photoItem, final int width, final int height){
        final Promise<Bitmap> bitmapPromise = new Promise<>();

        if(photoItem != null)
            new AsyncTask<String, String, String>(){

                @Override
                protected String doInBackground(String... params) {
                    try {
                        Bitmap bmp = Glide.with(context)
                                .load(photoItem.getReferal())
                                .asBitmap()
                                .into(width, height)
                                .get();

                        bitmapPromise.success(bmp);
                    } catch (InterruptedException e) {
                        bitmapPromise.failed(e.getMessage());
                    } catch (ExecutionException e) {
                        bitmapPromise.failed(e.getMessage());
                    }
                    return null;
                }
            }.execute();

        return bitmapPromise;
    }
}
