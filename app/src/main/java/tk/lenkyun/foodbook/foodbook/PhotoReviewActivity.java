package tk.lenkyun.foodbook.foodbook;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Service.PhotoContentService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.View.SquareImageView;

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

        FoodPost foodPost = null;
        Bitmap photo = null;

        try {
            foodPost = (FoodPost) Repository.getInstance().getData("post");
            photo = (Bitmap) Repository.getInstance().getData("photo");
        } catch (ClassCastException e) {
            finish();
            return;
        }

        if(foodPost == null){
            finish();
            return;
        }

        SquareImageView imageView = (SquareImageView)findViewById(R.id.reveiw_imageview);

        if(photo == null){
            imageView.setImageBitmap(photo);
        }

        PhotoContentService.getInstance().getPhotoContent(
                foodPost.getPostDetail().getPhoto(0),
                imageView
        );

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setRating(foodPost.getAverageRate());

        TextView caption = (TextView) findViewById(R.id.review_caption);
        caption.setText(foodPost.getPostDetail().getCaption());

        TextView date = (TextView) findViewById(R.id.date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD m YY H:i:s");
        date.setText(dateFormat.format(foodPost.getPostDetail().getCreatedDate()));
    }

}
