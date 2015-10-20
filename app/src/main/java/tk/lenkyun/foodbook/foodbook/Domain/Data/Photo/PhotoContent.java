package tk.lenkyun.foodbook.foodbook.Domain.Data.Photo;

import android.graphics.Bitmap;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Content;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

public class PhotoContent implements FoodbookType, Content<Bitmap> {
    protected Bitmap content;

    public PhotoContent(Bitmap content) {
        this.content = content;
    }

    @Override
    public Bitmap getContent() {
        return this.content;
    }

    @Override
    public void setContent(Bitmap content) {
        throw new UnsupportedOperationException("No support for PhotoContent to set its content.");
    }
}
