package tk.lenkyun.foodbook.foodbook.Data.Photo;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class Photo {
    private Uri referalImageURI;

    public Photo(Uri referalImageURI){
        this.referalImageURI = referalImageURI;
    }

    public void setReferalImageURI(Uri uri){
        referalImageURI = uri;
    }

    public Uri getReferalImageURI() {
        return referalImageURI;
    }
}
