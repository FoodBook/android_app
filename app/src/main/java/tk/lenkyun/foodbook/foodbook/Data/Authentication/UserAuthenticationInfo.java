package tk.lenkyun.foodbook.foodbook.Data.Authentication;

import tk.lenkyun.foodbook.foodbook.Data.User.User;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class UserAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "app/session";
    private String token;

    public UserAuthenticationInfo(User user, String token) {
        super(AUTH_TYPE);
        this.token = token;
    }

    @Override
    public String getAuthenticateInfo() {
        return token;
    }

    @Override
    public void setAuthenticateInfo(String authenticateInfo) {
        throw new UnsupportedOperationException("No support for UserAuthenticationInfo to set new token. (Must request from Tools)");
    }
}
