package tk.lenkyun.foodbook.foodbook.Data.User;

import com.facebook.AccessToken;

import tk.lenkyun.foodbook.foodbook.Data.User.AuthenUser;
import tk.lenkyun.foodbook.foodbook.Data.User.Profile;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class AuthenUserFacebook extends AuthenUser {
    private AccessToken fbToken;

    public AuthenUserFacebook(String username, AccessToken fbToken) {
        super(username);
    }

    public AccessToken getFbToken(){
        return fbToken;
    }

    @Override
    public String getAuthenticateInfo(){
        return fbToken.getToken();
    }

    @Override
    public String getAuthenticationType(){
        return "facebookid/accesstoken";
    }
}
