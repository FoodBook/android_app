package tk.lenkyun.foodbook.foodbook.Domain.Data.Photo;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Content;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

public class PhotoContent<E> implements FoodbookType, Content<E> {
    E content;

    public PhotoContent(E content) {
        this.content = content;
    }

    @Override
    public E getContent() {
        return this.content;
    }

    @Override
    public void setContent(E content) {
        throw new UnsupportedOperationException("No support for PhotoContent to set its content.");
    }
}
