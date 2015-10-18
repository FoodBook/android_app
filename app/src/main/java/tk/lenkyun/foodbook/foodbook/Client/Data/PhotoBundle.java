package tk.lenkyun.foodbook.foodbook.Client.Data;

import android.graphics.Bitmap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoContent;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class PhotoBundle implements Bundle<PhotoContent<Bitmap>> {
    private List<PhotoContent<Bitmap>> photoContentList = new LinkedList<>();

    public PhotoBundle(PhotoContent... photos) {
        for (PhotoContent photo : photos) {
            photoContentList.add(photo);
        }
    }

    @Override
    public PhotoContent<Bitmap> get(int index) {
        return photoContentList.get(index);
    }

    @Override
    public void put(PhotoContent<Bitmap> content) {
        photoContentList.add(content);
    }

    @Override
    public void remove(int index) {
        photoContentList.remove(index);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<PhotoContent<Bitmap>> iterator() {
        return photoContentList.iterator();
    }
}
