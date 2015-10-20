package tk.lenkyun.foodbook.foodbook.Client.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.apache.commons.collections4.MapIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class PhotoContentCache extends ContentCache<String, Object> {
    protected Context context = null;
    protected long timeToWriteCache;

    public PhotoContentCache(Context applicationContext, long timeToWriteCache, long timeToLive, long cacheCheckInterval, int maxItems) {
        super(timeToLive, cacheCheckInterval, maxItems);

        if (timeToWriteCache > timeToLive) {
            throw new IllegalArgumentException("timeToWriteCache must less than or equal timeToLive.");
        }

        this.timeToWriteCache = timeToWriteCache * 1000;

        this.context = applicationContext;
        if (applicationContext == null) {
            throw new NullPointerException("Application content can't be null.");
        }
    }

    public Bitmap get(PhotoItem photo) {
        return get(photo.getReferal().getPath());
    }

    @Override
    public Bitmap get(String key) {
        PhotoAccessObject c = (PhotoAccessObject) cache.get(key);
        if (c == null)
            return null;

        if (c.isRaw) {
            c.lastAccessed = System.currentTimeMillis();
            return (Bitmap) c.content;
        } else {
            try {
                c.content = readFromFile((Uri) c.content);
                c.isRaw = true;
                c.lastAccessed = System.currentTimeMillis();
            } catch (IOException e) {
                remove(key);
                return null;
            }
            return (Bitmap) c.content;
        }
    }

    public boolean has(PhotoItem photo) {
        return get(photo.getReferal().getPath()) != null;
    }

    public void put(PhotoItem photo, Bitmap bitmap) throws IOException {
        if (has(photo)) {
            remove(photo.getReferal().getPath());
        }

        put(photo.getReferal().getPath(), bitmap);
    }

    @Override
    public void put(String key, Object value) {
        synchronized (cache) {
            cache.put(key, new PhotoAccessObject(value));
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();

        long now = System.currentTimeMillis();
        ArrayList<String> transferKey = null;
        PhotoAccessObject c = null;

        synchronized (cache) {
            MapIterator itr = cache.mapIterator();

            transferKey = new ArrayList<String>((cache.size() / 2) + 1);
            String key = null;

            while (itr.hasNext()) {
                key = (String) itr.next();
                c = (PhotoAccessObject) itr.getValue();

                if (c != null && (now > c.lastAccessed + timeToWriteCache) && c.isRaw) {
                    transferKey.add(key);
                }
            }
        }

        for (String key : transferKey) {
            c = (PhotoAccessObject) cache.get(key);
            synchronized (c) {
                Bitmap bitmap = (Bitmap) c.content;
                Uri uri = null;
                try {
                    uri = writeToFile(bitmap);
                    if (uri == null) {
                        remove(key);
                    }
                } catch (IOException e) {
                    remove(key);
                } finally {
                    c.content = uri;
                    c.isRaw = false;
                }
            }

            Thread.yield();
        }
    }

    private Bitmap readFromFile(Uri uri) throws IOException {
        URL url = new URL(uri.getPath());
        return BitmapFactory.decodeStream(url.openStream());
    }

    // Refer from : http://stackoverflow.com/questions/3425906/creating-temporary-files-in-android
    private Uri writeToFile(Bitmap bitmap) throws IOException {
        File outputFile = File.createTempFile("foodbookcache", "jpg", context.getCacheDir());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(outputFile));

        return Uri.fromFile(outputFile);
    }

    protected class PhotoAccessObject extends ContentAccessObject {

        public boolean isRaw = true;

        public PhotoAccessObject(Object content) {
            super(content);
        }
    }
}
