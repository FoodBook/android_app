package tk.lenkyun.foodbook.foodbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import tk.lenkyun.foodbook.foodbook.Client.Data.Photo.PhotoItemParcelable;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.CameraHelper;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.GalleryHelper;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Helper.Service.FacebookHelper;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.LoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.LoginListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Client.Service.PhotoContentService;
import tk.lenkyun.foodbook.foodbook.Client.Service.PostFeedService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Content;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.NewsFeed;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final ObjectListener<PhotoItem> photoListener = new ObjectListener<PhotoItem>() {
        @Override
        public void onTaken(PhotoItem object, int extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.new_menu);
                    fab.close(false);
                }
            });
            Intent intent = new Intent(MainActivity.this, PhotoUploadActivity.class);
            intent.putExtra("orientation", extra);
            intent.putExtra("photo", PhotoItemParcelable.fromPhotoItem(object));
            startActivityForResult(intent, PhotoUploadActivity.INTENT_ID);
        }
    };
    private CameraHelper cameraHelper;
    private GalleryHelper galleryHelper;
    private NewsFeed mNewsFeed;
    private RecyclerView recyclerView;
    private LoginActivity loginActivity;
    private LoginListener loginListener = new LoginListener() {
        @Override
        public void onLoginSuccess(LoginService loginService, User user) {
            //updateProfileUI();
        }

        @Override
        public void onLoginFailed(LoginService loginService, AuthenticationInfo authenticationInfo, LoginException login) {
        }

        @Override
        public void onLogout(LoginService loginService) {
            uiCheckLogin();
        }
    };

    public SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        PhotoContentService.initialize(getApplicationContext());

        // Create global configuration and initialize ImageLoader with this config
        uiCheckLogin();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                updateNewsFeed();
            }
        });


        initCamera();
        initGallery();

        initNewsFeed();

        profile = (ImageView) findViewById(R.id.profile_picture);
        profileCover = (LinearLayout) findViewById(R.id.cover_layout);
        userFullname = (TextView) findViewById(R.id.user_fullname);
    }

    private void initGallery() {
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryHelper.take();
            }
        });

        galleryHelper = new GalleryHelper(this);
        galleryHelper.registerListener(photoListener);
    }

    private void initCamera() {
        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraHelper.take();
            }
        });

        cameraHelper = new CameraHelper(this);
        cameraHelper.setListener(photoListener);
    }

    private void initNewsFeed() {
        recyclerView = (RecyclerView) findViewById(R.id.newsfeed_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PostAdapter postAdapter = new PostAdapter(mNewsFeed);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);
        //updateNewsFeed();

        recyclerView.setHasFixedSize(true);
    }

    public void updateNewsFeed() {
        mSwipeRefreshLayout.setRefreshing(true);
        PostFeedService.getInstance().getNewsFeed()
        .onSuccess(new PromiseRun<NewsFeed>() {
            @Override
            public void run(String status, NewsFeed result) {
                mNewsFeed = result;
                final PostAdapter postAdapter = new PostAdapter(mNewsFeed);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(postAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).onFailed(new PromiseRun<NewsFeed>() {
            @Override
            public void run(final String status, NewsFeed result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "newsfeed loading failed, " + status, Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void uiCheckLogin() {
        if (!LoginService.getInstance().validateCurrentSession()) {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivityForResult(loginActivity, LoginActivity.INTENT_ID);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_account){
            Intent intent = new Intent(getApplicationContext(), EditProfilePopupActivity.class);
            startActivityForResult(intent, EditProfilePopupActivity.INTENT_ID);
        }
        if (id == R.id.nav_logout){
            LoginService.getInstance().logout();
            uiCheckLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        cameraHelper.onActivityResult(requestCode, resultCode, data);
        galleryHelper.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EditProfilePopupActivity.INTENT_ID:
                updateProfileUI();
                updateNewsFeed();
                break;
            case LoginActivity.INTENT_ID:
                updateProfileUI();
                updateNewsFeed();
                break;
            case PhotoUploadActivity.INTENT_ID:
                updateNewsFeed();
                break;
        }
    }

    private void updateProfileUI() {
        if (!LoginService.getInstance().validateCurrentSession()) {
            return;
        }

        LoginService.getInstance().getUser().onSuccess(
                new PromiseRun<User>() {
                    @Override
                    public void run(String status, final User result) {
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              updateProfileUI2(result);
                                          }
                                      });
                    }
                }
        );
    }

    private ImageView profile = null;
    private LinearLayout profileCover = null;
    private TextView userFullname = null;

    private void updateProfileUI2(User user){
        final Profile userProfile = user.getProfile();
        final PhotoItem profilePicture = userProfile.getProfilePicture();

        final MainActivity self = this;

        PhotoContentService.getInstance().getPhotoContent(
                userProfile.getProfilePicture(),
                profile
        );

        if (userProfile.getCoverPicture() != null && userProfile.getCoverPicture().getReferal() != null)

            PhotoContentService.getInstance().getPhotoContent(
                    userProfile.getCoverPicture())
                    .onSuccess(new PromiseRun<Bitmap>() {
                        @Override
                        public void run(String status, final Bitmap result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap scaled = Bitmap.createScaledBitmap(result,
                                            200, 100, false);
                                    Canvas canvas = new Canvas(scaled);
                                    Paint paint = new Paint();
                                    paint.setARGB(150, 0, 0, 0);
                                    canvas.drawRect(0, 0, 200, 100, paint);

                                    profileCover.setBackgroundDrawable(new BitmapDrawable(getResources(), scaled));
                                }
                            });
                        }
                    });

        if(userFullname != null)
            userFullname.setText(String.format(getResources().getString(R.string.profile_name_display),
                userProfile.getFirstname()));
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName, caption, date;
        public ImageView feedPhoto, profilePhoto;

        public PostViewHolder(View itemView) {
            super(itemView);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            caption = (TextView) itemView.findViewById(R.id.caption);

            feedPhoto = (ImageView) itemView.findViewById(R.id.foodbook_pic);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profile_picture);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
        NewsFeed newsFeed;
        int counter = 0;

        public PostAdapter(NewsFeed newsFeed) {
            this.newsFeed = newsFeed;
            if (newsFeed != null) {
                counter = newsFeed.countFoodPost();
            }
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed, parent, false);
            return new PostViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final PostViewHolder holder, int position) {
            PostFeedService newsFeedService = PostFeedService.getInstance();
            final FoodPost foodPost = newsFeed.getFoodPost(position);
            if (foodPost == null) {
                return;
            }
            final User owner = foodPost.getOwner();
            final Profile ownerProfile = owner.getProfile();

            holder.feedPhoto.setImageResource(0);
            holder.profilePhoto.setImageResource(0);

            holder.profilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Repository.getInstance().setData("user", owner);
                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    startActivity(intent);
                }
            });

            holder.profileName.setText(String.format(
                    getResources().getString(R.string.profile_name_display),
                    ownerProfile.getFirstname()
            ));
            holder.caption.setText(foodPost.getPostDetail().getCaption());

            holder.feedPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PhotoReviewActivity.class);
                    Repository.getInstance().setData("post", foodPost);
                    startActivity(intent);
                }
            });

            PhotoContentService.getInstance().getPhotoContent(
                    foodPost.getPostDetail().getPhoto(0))
                    .onSuccess(new PromiseRun<Bitmap>() {
                        @Override
                        public void run(String status, final Bitmap result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.feedPhoto.setImageBitmap(result);
                                }
                            });
                        }
                    });

            PhotoContentService.getInstance().getPhotoContent(
                    ownerProfile.getProfilePicture())
                    .onSuccess(new PromiseRun<Bitmap>() {
                        @Override
                        public void run(String status, final Bitmap result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.profilePhoto.setImageBitmap(result);
                                }
                            });
                        }
                    });
        }

        @Override
        public int getItemCount() {
            if (newsFeed != null) {
                return counter;
            }
            return 0;
        }
    }
}
