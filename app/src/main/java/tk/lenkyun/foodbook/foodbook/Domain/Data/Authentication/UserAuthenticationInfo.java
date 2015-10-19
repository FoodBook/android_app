package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class UserAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "app/session";
    private String token;
    private String userid;

    public UserAuthenticationInfo(String id, String token) {
        super(AUTH_TYPE);
        this.token = token;
        this.userid = id;
    }

    @Override
    public String getAuthenticateId() {
        return userid;
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
