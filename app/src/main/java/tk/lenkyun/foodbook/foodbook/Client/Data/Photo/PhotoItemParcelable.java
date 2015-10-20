package tk.lenkyun.foodbook.foodbook.Client.Data.Photo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 20/10/2558.
 */
public class PhotoItemParcelable extends PhotoItem implements Parcelable {

    public static final Creator<PhotoItemParcelable> CREATOR = new Creator<PhotoItemParcelable>() {
        @Override
        public PhotoItemParcelable createFromParcel(Parcel in) {
            return new PhotoItemParcelable(in);
        }

        @Override
        public PhotoItemParcelable[] newArray(int size) {
            return new PhotoItemParcelable[size];
        }
    };

    public PhotoItemParcelable(Uri content, int width, int height) {
        super(content, width, height);
    }

    // Parcel
    //
    protected PhotoItemParcelable(Parcel in) {
        super(null, 0, 0);
        referalImageURI = Uri.parse(in.readString());
        width = in.readInt();
        height = in.readInt();
    }

    public static PhotoItemParcelable fromPhotoItem(PhotoItem photoItem) {
        return new PhotoItemParcelable(photoItem.getReferal(), photoItem.getWidth(), photoItem.getHeight());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referalImageURI.toString());
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
