package tk.lenkyun.foodbook.foodbook.Data.User;

import com.facebook.AccessToken;

public class AuthenUserFacebook extends AuthenUser {
    private AccessToken facebookToken;

    public AuthenUserFacebook(String username, AccessToken facebookToken) {
        super(username);
    }

    public AccessToken getFacebookToken() {
        return facebookToken;
    }

    @Override
    public String getAuthenticateInfo(){
        return facebookToken.getToken();
    }

    @Override
    public String getAuthenticationType(){
        return "facebookid/accesstoken";
    }
}
