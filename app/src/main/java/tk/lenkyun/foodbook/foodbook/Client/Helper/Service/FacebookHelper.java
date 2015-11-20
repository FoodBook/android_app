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

import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionRequest;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionResult;
import tk.lenkyun.foodbook.foodbook.Adapter.HTTPAdapter;
import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.DataListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Config;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.FacebookAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class FacebookHelper {
    private static FacebookHelper instance = null;
    private static Object lock = new Object();
    private ConnectionAdapter connectionAdapter;

    public FacebookHelper(HTTPAdapter httpAdapter) {
        this.connectionAdapter = httpAdapter;
    }

    /**
     * Get service instance if not exists
     *
     * @return A service instance
     */
    public static FacebookHelper getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new FacebookHelper(new HTTPAdapter(Config.SERVER));
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

        final Promise<User> userPromise = new Promise<>();
        ConnectionRequest request = connectionAdapter.createRequest();

        Promise<ConnectionResult> result = request.addServicePath("oauth").addServicePath("login").addServicePath("facebook")
            .setSubmit(true)
            .setDataInputParam(new FacebookAuthenticationInfo(null, AccessToken.getCurrentAccessToken().getToken()))
            .execute();

        result.onSuccess(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                if(result.isError()){
                    userPromise.failed("0");
                }else{
                    LoginService.getInstance().updateSession(result.getResult(
                            SessionAuthenticationInfo.class
                    ));

                    LoginService.getInstance().getUser().bind(userPromise);
                }
            }
        });

        return userPromise;
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
