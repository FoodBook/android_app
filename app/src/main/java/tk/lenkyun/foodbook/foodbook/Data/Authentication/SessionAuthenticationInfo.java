package tk.lenkyun.foodbook.foodbook.Data.Authentication;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class SessionAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "app/session";
    private String token;
    private String userId, username;

    public SessionAuthenticationInfo(String userId, String username, String token) {
        super(AUTH_TYPE);
        this.token = token;
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String getAuthenticateInfo() {
        return token;
    }

    @Override
    public void setAuthenticateInfo(String authenticateInfo) {
        throw new UnsupportedOperationException("No support for FacebookAuthenticationInfo to set new token. (Must request from Tools)");
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
