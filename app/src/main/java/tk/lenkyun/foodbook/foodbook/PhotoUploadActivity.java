package tk.lenkyun.foodbook.foodbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.places.Place;

import tk.lenkyun.foodbook.foodbook.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Data.Location;
import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;
import tk.lenkyun.foodbook.foodbook.Data.PostDetail;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.PlaceHelper;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Client.Service.NewsFeedService;

public class PhotoUploadActivity extends AppCompatActivity {

    private PlaceHelper placeHelper = new PlaceHelper(this);
    private Place place = null;

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_photo_upload);

        final Photo photo = (Photo) Repository.getInstance().getData("UploadPhoto");
        Bitmap bitmapX = photo.getBitmap();

        float height = bitmapX.getHeight();
        float width = bitmapX.getWidth();

        float ratio = 1;
        if(width > height){
            ratio = 480F / height;
        }else{
            ratio = 640F / width;
        }

        Bitmap bmp = Bitmap.createScaledBitmap(bitmapX, (int)(bitmapX.getWidth() * ratio), (int)(bitmapX.getHeight() * ratio), false);
        bitmapX = null;
        photo.updateBitmap(bmp);

        if(photo != null){
            ImageView imageView = (ImageView) findViewById(R.id.upload_imageview);
            Bitmap bitmap = photo.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setARGB(50, 0, 0, 0);

            canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

            imageView.setImageBitmap(bitmap);
        }

        final TextView placeTitle = (TextView) findViewById(R.id.upload_place_title);
        final TextView placeDesc = (TextView) findViewById(R.id.upload_place_desc);

        placeHelper.registerPlaceListener(new ObjectListener<Place>() {
            @Override
            public void onTaken(final Place object, int extra) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        place = object;
                        placeTitle.setText(object.getName());
                        placeDesc.setText(object.getAddress());
                    }
                });
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.location_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeHelper.pick();
            }
        });

        final EditText caption = (EditText) findViewById(R.id.upload_caption);

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.upload_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          if(place == null){
                              caption.setError("Please enter location");
                              return;
                          }

                          PostDetail postDetail = new PostDetail(caption.getText().toString(),
                                  new Location(place.getName().toString(), place.getLatLng().toString()));
                          postDetail.addPhoto(photo);

                          FoodPost foodPost = new FoodPost(String.valueOf(new Object().hashCode()),
                                  postDetail,
                                  LoginService.getInstance().getUser());

                          Log.e("Upload", LoginService.getInstance().getUser().getId());

                          NewsFeedService.getInstance().publishFoodPost(foodPost);
                          finish();
                      }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        placeHelper.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
    }

}
