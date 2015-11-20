package tk.lenkyun.foodbook.foodbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.isseiaoki.simplecropview.CropImageView;

import java.io.IOException;

import tk.lenkyun.foodbook.foodbook.Client.Data.Photo.PhotoItemParcelable;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.PlaceHelper;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Service.PostFeedService;
import tk.lenkyun.foodbook.foodbook.Client.Utils.PhotoUtils;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

public class PhotoUploadActivity extends AppCompatActivity {

    public static final int INTENT_ID = 100;
    public static final int PORTRAIT = 0, LANDSCAPE = 1;
    public static final float MAX_PHOTO_RATIO = 2f;
    private Place place = null;
    private PlaceHelper placeHelper = new PlaceHelper(this);
    private Bitmap mBitmap;
    private float mRatio = 1;
    private int mRotation = LANDSCAPE;
    private LinearLayout mOverall;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_photo_upload);

        if (initPhotoView() == false) {
            finish();
            return;
        }

        initSubmit();
        initPlacePicker();

        mOverall = (LinearLayout) findViewById(R.id.overall);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mOverall.setVisibility(show ? View.GONE : View.VISIBLE);
            mOverall.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mOverall.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mOverall.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void initSubmit() {
        final EditText caption = (EditText) findViewById(R.id.upload_caption);

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.upload_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (place == null) {
//                            onPublishError("Please enter location");
//                            return;
//                        }

                        showProgress(true);
                        CropImageView cropImageView = (CropImageView) findViewById(R.id.upload_imageview);
                        PhotoBundle photoBundle = new PhotoBundle(
                                new PhotoContent(cropImageView.getCroppedBitmap())
                        );

                        String placeName = "";
                        LatLng latLng = null;
                        if (place != null) {
                            placeName = place.getName().toString();
                            latLng = place.getLatLng();
                        }

                        showProgressbar();
                        Location location;
                        if(latLng == null){
                            location = new Location(placeName, new Location.LatLng(13.5, 100), null);
                        }else{
                            location = new Location(placeName, new Location.LatLng(latLng.latitude, latLng.longitude), null);
                        }

                        Promise<FoodPost> foodPostPromise = PostFeedService.getInstance().publishFoodPost(
                                caption.getText().toString(),
                                location,
                                photoBundle);

                        if(foodPostPromise == null){
                            onPublishError("Unknown error occurred!");
                        }else {
                            foodPostPromise
                                    .onSuccess(new PromiseRun<FoodPost>() {
                                        @Override
                                        public void run(String status, FoodPost result) {
                                            Repository.getInstance().setData("upload_result", result);
                                            finish();
                                        }
                                    })
                                    .onFailed(new PromiseRun<FoodPost>() {
                                        @Override
                                        public void run(String status, FoodPost result) {
                                            showText(status);
                                            finish();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    private void showText(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PhotoUploadActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setProgressBarIndeterminateVisibility(true);
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
                Intent intent = new Intent (getApplicationContext(), PickLocationActivity.class);
                startActivity(intent);
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

        if(bitmapX.getHeight() < bitmapX.getWidth()) {
            bitmapX = Bitmap.createBitmap(bitmapX, bitmapX.getWidth() / 2 - bitmapX.getHeight() / 2, 0, bitmapX.getHeight(), bitmapX.getHeight());
        }else{
            bitmapX = Bitmap.createBitmap(bitmapX, 0, bitmapX.getHeight() / 2 - bitmapX.getWidth() / 2, bitmapX.getWidth(), bitmapX.getWidth());
        }

        ImageView imageView = (ImageView) findViewById(R.id.upload_imageview);
        imageView.setImageBitmap(bitmapX);

        mBitmap = bitmapX;
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //setPhotoViewRatio(mRotation, mRatio);
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

    private void onPublishError(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        placeHelper.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
    }

}
