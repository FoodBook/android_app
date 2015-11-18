package tk.lenkyun.foodbook.foodbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;

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

        final PhotoContent photo;
        final FoodPost foodPost;
        try {
            photo = (PhotoContent) Repository.getInstance().getData("review");
            foodPost = (FoodPost) Repository.getInstance().getData("foodpost");
        } catch (ClassCastException e) {
            finish();
            return;
        }

        if (photo == null) {
            finish();
            return;
        }

        ImageView imageView = (ImageView)findViewById(R.id.reveiw_imageview);
        imageView.setImageBitmap(photo.getContent());
        //RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        //ratingBar.setRating();
        TextView caption = (TextView) findViewById(R.id.review_caption);
        caption.setText(foodPost.getPostDetail().getCaption().toString());

        EditText date = (EditText) findViewById(R.id.date);
        date.setText(foodPost.getPostDetail().getCreatedDate().toString());

    }

}
