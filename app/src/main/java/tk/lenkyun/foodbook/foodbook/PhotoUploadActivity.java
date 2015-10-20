package tk.lenkyun.foodbook.foodbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.places.Place;

import java.io.IOException;

import tk.lenkyun.foodbook.foodbook.Client.Data.Photo.PhotoItemParcelable;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.PlaceHelper;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.RequestException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.RequestListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.PostFeedService;
import tk.lenkyun.foodbook.foodbook.Client.Utils.PhotoUtils;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;

public class PhotoUploadActivity extends AppCompatActivity {

    public static final int INTENT_ID = 100;
    public static final int PORTRAIT = 0, LANDSCAPE = 1;
    public static final float MAX_PHOTO_RATIO = 2f;
    private Place place = null;
    private PlaceHelper placeHelper = new PlaceHelper(this);
    private Bitmap mBitmap;
    private float mRatio = 1;
    private int mRotation = LANDSCAPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_photo_upload);

        if (initPhotoView() == false) {
            finish();
            return;
        }

        initPlacePicker();
    }

    private void initSumbit() {
        final EditText caption = (EditText) findViewById(R.id.upload_caption);

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.upload_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (place == null) {
                            onPublishError("Please enter location");
                            return;
                        }

                        PhotoBundle photoBundle = new PhotoBundle(
                                new PhotoContent(mBitmap)
                        );

                        PostFeedService.getInstance().publishFoodPost(caption.getText().toString(),
                                new Location(place.getName().toString(), place.getLatLng().toString()),
                                photoBundle, new RequestListener<FoodPost>() {
                                    @Override
                                    public void onComplete(FoodPost result) {
                                        finish();
                                    }

                                    @Override
                                    public void onFailed(final RequestException e) {
                                        onPublishError(e.getMessage());
                                    }
                                });
                    }
                });
            }
        });
    }

    private void initPlacePicker() {
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
    }

    private void setPhotoViewRatio(int rotation, float ratio) {
        LinearLayout imageView = (LinearLayout) findViewById(R.id.upload_imageview_layout);
        float padding = getResources().getDimension(R.dimen.activity_horizontal_margin);
        float width = getWindowManager().getDefaultDisplay().getWidth() - padding;

        if (ratio > MAX_PHOTO_RATIO)
            ratio = MAX_PHOTO_RATIO;

        switch (rotation) {
            case PORTRAIT:
                imageView.getLayoutParams().height = (int) (width * ratio);
                break;
            case LANDSCAPE:
                imageView.getLayoutParams().height = (int) (width * (1f / ratio));
                break;
        }
    }

    private boolean initPhotoView() {
        PhotoItem photo;
        try {
            photo = (PhotoItemParcelable) getIntent().getExtras().getParcelable("photo");
        } catch (ClassCastException e) {
            return false;
        }

        if (photo == null) {
            return false;
        }

        Bitmap bitmapX;
        try {
            bitmapX = PhotoUtils.readBitmap(this, photo).getContent();
        } catch (IOException e) {
            return false;
        }

        float height = bitmapX.getHeight(),
                width = bitmapX.getWidth();

        if (width > height) {
            mRatio = 480F / height;
            mRotation = LANDSCAPE;
        } else {
            mRatio = 640F / width;
            mRotation = PORTRAIT;
        }

        bitmapX = Bitmap.createScaledBitmap(bitmapX,
                (int) (bitmapX.getWidth() * mRatio),
                (int) (bitmapX.getHeight() * mRatio), false);
        bitmapX = bitmapX.copy(bitmapX.getConfig(), true);

        if (mRotation == LANDSCAPE) {
            mRatio = width / height;
        } else {
            mRatio = height / width;
        }

        Paint paint = new Paint();
        paint.setARGB(50, 0, 0, 0);

        Canvas canvas = new Canvas(bitmapX);
        canvas.drawRect(0, 0, bitmapX.getWidth(), bitmapX.getHeight(), paint);

        ImageView imageView = (ImageView) findViewById(R.id.upload_imageview);
        imageView.setImageBitmap(bitmapX);

        initRatio();
        mBitmap = bitmapX;
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        setPhotoViewRatio(mRotation, mRatio);
    }

    private void initRatio() {
//        setPhotoViewRatio(mRotation, mRatio);
//        ViewTreeObserver vto = imageView.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
//            @Override
//            public boolean onPreDraw() {
//                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
//                setPhotoViewRatio(mRotation, mRatio);
//                return true;
//            }
//        });
    }

    private void onPublishError(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        placeHelper.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
    }

}
