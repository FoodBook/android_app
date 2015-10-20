package tk.lenkyun.foodbook.foodbook.Domain.Operation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class PhotoBundle implements Bundle<PhotoContent> {
    private List<PhotoContent> photoContentList = new LinkedList<>();

    public PhotoBundle(PhotoContent... photos) {
        for (PhotoContent photo : photos) {
            photoContentList.add(photo);
        }
    }

    @Override
    public PhotoContent get(int index) {
        return photoContentList.get(index);
    }

    @Override
    public void put(PhotoContent content) {
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
    public Iterator<PhotoContent> iterator() {
        return photoContentList.iterator();
    }
}
