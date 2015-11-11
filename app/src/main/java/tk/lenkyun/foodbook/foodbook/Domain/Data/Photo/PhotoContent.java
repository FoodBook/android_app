package tk.lenkyun.foodbook.foodbook.Domain.Data.Photo;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Content;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;
import tk.lenkyun.foodbook.foodbook.Domain.Jackson.PhotoContentDeserializer;
import tk.lenkyun.foodbook.foodbook.Domain.Jackson.PhotoContentSerializer;

public class PhotoContent implements FoodbookType, Content<Bitmap> {
    @JsonSerialize(using = PhotoContentSerializer.class)
    @JsonDeserialize(using = PhotoContentDeserializer.class)
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
