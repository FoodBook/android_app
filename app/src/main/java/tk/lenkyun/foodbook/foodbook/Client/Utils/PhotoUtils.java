package tk.lenkyun.foodbook.foodbook.Client.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 20/10/2558.
 */
public class PhotoUtils {
    public static PhotoContent readBitmap(Activity activity, PhotoItem content) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), content.getReferal());
        return new PhotoContent(bitmap);
    }

    public static Uri writeBitmapToFile(Bitmap bitmap, File file) throws IOException {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

        return Uri.fromFile(file);
    }
}
