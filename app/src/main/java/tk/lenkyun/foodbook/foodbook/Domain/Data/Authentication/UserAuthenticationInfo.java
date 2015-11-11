package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class UserAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "username/password";
    private String password;
    private String userid;

    public UserAuthenticationInfo(String id, String password) {
        super(AUTH_TYPE);
        this.password = password;
        this.userid = id;
    }

    @Override
    public String getId() {
        return userid;
    }

    @Override
    public String getInfo() {
        return password;
    }

    @Override
    public void setAuthenticateInfo(String authenticateInfo) {
        throw new UnsupportedOperationException("No support for UserAuthenticationInfo to set new password. (Must execute from Tools)");
    }
}
