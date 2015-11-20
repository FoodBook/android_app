package tk.lenkyun.foodbook.foodbook;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Repository;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Client.Service.PhotoContentService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

/**
 * Created by lenkyun on 19/11/2558.
 */
public class UserInfoActivity extends Activity {
    public static final int INTENT_ID = 1013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_profile);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        final ImageView myImage = (ImageView) findViewById(R.id.my_picture);
        final EditText myName = (EditText) findViewById(R.id.my_name);

        final User result = (User) Repository.getInstance().getData("user");

        PhotoContentService.getInstance().getPhotoContent(
                result.getProfile().getProfilePicture(),
                myImage
        );
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myName.setText(result.getProfile().getFirstname());
            }
        });

        myName.setEnabled(false);

        final Button submit = (Button) findViewById(R.id.button2);
        submit.setVisibility(View.GONE);
    }
}
