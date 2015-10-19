package tk.lenkyun.foodbook.foodbook.Domain.Data.Photo;

import android.net.Uri;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Referal;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class PhotoItem implements FoodbookType, Referal {
    private Uri referalImageURI;
    private int width, height;

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
        referalImageURI = uri;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
