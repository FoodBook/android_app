package tk.lenkyun.foodbook.foodbook.Domain.Data.Photo;

import android.net.Uri;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Referal;
import tk.lenkyun.foodbook.foodbook.Domain.Jackson.UriDeserializer;
import tk.lenkyun.foodbook.foodbook.Domain.Jackson.UriSerializer;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoItem implements FoodbookType, Referal {
    public static final int UNKNOWN_WIDTH = -1, UNKNOWN_HEIGHT = -1;

    @JsonSerialize(using = UriSerializer.class)
    @JsonDeserialize(using = UriDeserializer.class)
    protected Uri referalImageURI;

    protected int width, height;

    public PhotoItem(){}
    public PhotoItem(Uri referalImageURI, int width, int height) {
        this.referalImageURI = referalImageURI;
        this.width = width;
        this.height = height;
    }

    @Override
    public Uri getReferal() {
        return referalImageURI;
    }

    @Override
    public void setReferal(Uri uri) {
        this.referalImageURI = uri;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
