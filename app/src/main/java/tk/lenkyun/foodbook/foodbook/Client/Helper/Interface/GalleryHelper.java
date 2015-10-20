package tk.lenkyun.foodbook.foodbook.Client.Helper.Interface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

public class GalleryHelper {
    private final int INTENT_ID = 1125;
    private Activity activity;
    private List<ObjectListener<PhotoItem>> photoListeners = new LinkedList<>();

    public GalleryHelper(Activity activity){
        this.activity = activity;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == INTENT_ID && resultCode == Activity.RESULT_OK && intent != null && intent.getData() != null) {
            Uri uri = intent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);

                ExifInterface ei = new ExifInterface(uri.toString());
                String orientString = ei.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
                int orientationD = 0;

                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        orientationD = 90;
                        bitmap = rotateImage(bitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        orientationD = 180;
                        bitmap = rotateImage(bitmap, 180);
                        break;
                    // etc.
                }

                PhotoItem photo = new PhotoItem(uri, bitmap.getWidth(), bitmap.getHeight());
                for (ObjectListener<PhotoItem> photoListener : photoListeners) {
                    photoListener.onTaken(photo, orientationD);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap rotateImage(Bitmap bitmap, int degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(),true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    public void registerListener(ObjectListener<PhotoItem> photoListener) {
        photoListeners.add(photoListener);
    }

    public void take() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        activity.startActivityForResult(Intent.createChooser(intent,
                "Complete action using"), INTENT_ID);
    }
}
