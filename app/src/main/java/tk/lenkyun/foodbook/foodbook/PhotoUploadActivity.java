package tk.lenkyun.foodbook.foodbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.places.Place;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.PlaceHelper;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Service.PostFeedService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;

public class PhotoUploadActivity extends AppCompatActivity {

    public static final int INTENT_ID = 100;
    private PlaceHelper placeHelper = new PlaceHelper(this);
    private Place place = null;

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_photo_upload);

        PhotoContent<Bitmap> photo;
        try {
            photo = (PhotoContent<Bitmap>) Repository.getInstance().getData("UploadPhoto");
        } catch (ClassCastException e) {
            finish();
            return;
        }

        if (photo == null) {
            finish();
            return;
        }
        final Bitmap bitmapX = photo.getContent();

        float height = bitmapX.getHeight();
        float width = bitmapX.getWidth();

        float ratio;
        if (width > height) {
            ratio = 480F / height;
        } else {
            ratio = 640F / width;
        }

        Bitmap bmp = Bitmap.createScaledBitmap(bitmapX, (int) (bitmapX.getWidth() * ratio), (int) (bitmapX.getHeight() * ratio), false);

        ImageView imageView = (ImageView) findViewById(R.id.upload_imageview);
        final Bitmap bitmap = bmp;
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setARGB(50, 0, 0, 0);

        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        imageView.setImageBitmap(bitmap);

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

                          PhotoBundle photoBundle = new PhotoBundle(
                                  new PhotoContent<Bitmap>(bitmap)
                          );

                          PostFeedService.getInstance().publishFoodPost(caption.getText().toString(),
                                  new Location(place.getName().toString(), place.getLatLng().toString()),
                                  photoBundle);

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
