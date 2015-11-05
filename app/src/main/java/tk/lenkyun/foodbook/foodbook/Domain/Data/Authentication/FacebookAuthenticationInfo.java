package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

import com.facebook.AccessToken;

public class FacebookAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "facebook/token";
    private String facebookToken;
    private String facebookId;

    public FacebookAuthenticationInfo(String id, AccessToken facebookToken) {
        this(id, facebookToken.getToken());
    }

    public FacebookAuthenticationInfo(String id, String facebookToken) {
        super(AUTH_TYPE);
        this.facebookToken = facebookToken;
        this.facebookId = id;
    }

    @Override
    public String getId() {
        return facebookId;
    }

    @Override
    public String getInfo() {
        return facebookToken;
    }

    @Override
    public void setAuthenticateInfo(String authenticateInfo) {
        throw new UnsupportedOperationException("No support for FacebookAuthenticationInfo to set new token. (Must request from Tools)");
    }
}
