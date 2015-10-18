package tk.lenkyun.foodbook.foodbook.Data.Photo;

import android.net.Uri;

import tk.lenkyun.foodbook.foodbook.Data.Content;

public class ContentPhoto<E> extends Photo implements Content<E>{
    E content;

    public ContentPhoto(E content, Uri referalImageURI) {
        super(referalImageURI);
    }

    public static <E> ContentPhoto<E> fromPhoto(Photo photo) {
        return new ContentPhoto<>(null, photo.getReferalImageURI());
    }

    public static <E> ContentPhoto<E> fromContent(E content) {
        return new ContentPhoto<>(content, null);
    }

    public static <E> ContentPhoto<E> fromContent(E content, Photo photo) {
        return new ContentPhoto<>(content, photo.getReferalImageURI());
    }

    public static <E> ContentPhoto<E> fromPhoto(Photo photo, E content) {
        Uri refer = null;
        if (photo != null)
            refer = photo.getReferalImageURI();
        return new ContentPhoto<>(content, refer);
    }

    @Override
    public String getContentType() {
        return content.getClass().getName();
    }

    @Override
    public E getContent() {
        return this.content;
    }

    @Override
    public void setContent(E content) {
        this.content = content;
    }
}
