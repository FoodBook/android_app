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

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.DataListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;
import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Data.User.AuthenUser;
import tk.lenkyun.foodbook.foodbook.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Data.User.Profile;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class FacebookHelper {
    public User login(){
        // TODO : Implement real
        if(AccessToken.getCurrentAccessToken() == null){
            return null;
        }

        // Debug info accept any user
        Profile profile = getFBProfile();

        if(profile != null) {
            AuthenUser authenUser = new AuthenUser(DebugInfo.USERNAME);
            authenUser.setAuthenticateInfo(DebugInfo.PASSWORD);

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

    public boolean getCoverPicture(final DataListener<Photo> result){
        if(!validateLogin()){
            result.onFailed("No login");
            return false;
        }

        final Photo photo = new Photo(null);
        final GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            JSONObject cover = object.getJSONObject("cover");
                            photo.setReferalImageURI(Uri.parse(cover.getString("source")));
                            result.onLoaded(photo);
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
                    fbProfile.getLastName(), new Photo(fbProfile.getProfilePictureUri(300, 300)));
            return profile;
        }else{
            return null;
        }
    }

    private static FacebookHelper instance = null;
    private static Object lock = new Object();

    public boolean validateLogin(){
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static FacebookHelper getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new FacebookHelper();
                }
            }
        }

        return instance;
    }
}
