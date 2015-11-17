package tk.lenkyun.foodbook.foodbook;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Data.Photo.ContentPhoto;

public class PhotoReviewActivity extends AppCompatActivity {

    public static final int INTENT_ID = 200;
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_photo_review);

        final ContentPhoto<Bitmap> photo;
        try {
            photo = (ContentPhoto<Bitmap>) Repository.getInstance().getData("UploadPhoto");
        } catch (ClassCastException e) {
            finish();
            return;
        }

        if (photo == null) {
            finish();
            return;
        }
    }

}
