package tk.lenkyun.foodbook.foodbook.Client.Helper.Service;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.DataListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class FacebookHelper {
    private static FacebookHelper instance = null;
    private static Object lock = new Object();

    /**
     * Get service instance if not exists
     *
     * @return A service instance
     */
    public static FacebookHelper getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new FacebookHelper();
                }
            }
        }

        return instance;
    }

    public Promise<User> login(){
        // TODO : Implement real
        if(AccessToken.getCurrentAccessToken() == null){
            return null;
        }

        // Debug info accept any user
        Profile profile = getFBProfile();

        if(profile != null) {
            UserAuthenticationInfo authenUser = new UserAuthenticationInfo(DebugInfo.USERNAME, DebugInfo.PASSWORD);

            // Dummy login
            LoginService.getInstance().login(authenUser);
            return LoginService.getInstance().getUser();
        }else{
            return null;
        }
    }

    public void logout(){
        LoginManager.getInstance().logOut();
        // Foodbook
        LoginService.getInstance().logout();
    }

    public boolean getCoverPicture(final DataListener<PhotoItem> result){
        if(!validateLogin()){
            result.onFailed("No login");
            return false;
        }

        final PhotoItem photoItem = new PhotoItem(null, PhotoItem.UNKNOWN_WIDTH, PhotoItem.UNKNOWN_HEIGHT);
        final GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            JSONObject cover = object.getJSONObject("cover");
                            photoItem.setReferal(Uri.parse(cover.getString("source")));
                            result.onLoaded(photoItem);
                        } catch (JSONException e){ }
                    }
                }
        );
        Bundle parameter = new Bundle();
        parameter.putString("fields", "cover");
        request.setParameters(parameter);

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                request.executeAndWait();
                return null;
            }
        }.execute();

        return true;
    }

    public Profile getFBProfile(){
        com.facebook.Profile fbProfile = com.facebook.Profile.getCurrentProfile();

        if(fbProfile != null) {
            Profile profile = new Profile(fbProfile.getFirstName(),
                    fbProfile.getLastName(), new PhotoItem(fbProfile.getProfilePictureUri(300, 300), 300, 300));
            return profile;
        }else{
            return null;
        }
    }

    public boolean validateLogin(){
        return AccessToken.getCurrentAccessToken() != null;
    }
}
