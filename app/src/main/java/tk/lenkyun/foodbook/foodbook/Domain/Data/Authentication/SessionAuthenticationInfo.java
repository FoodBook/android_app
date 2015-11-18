package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class SessionAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "oauth";
    private String token;
    private String userId;

    public SessionAuthenticationInfo(String userId, String token) {
        super(AUTH_TYPE);
        this.token = token;
        this.userId = userId;
    }

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String getInfo() {
        return token;
    }

    @Override
    public void setId(String id) {
        userId = id;
    }

    @Override
    public void setInfo(String info) {
        token = info;
    }

    @Override
    public void setAuthenticationType(String authenticateInfo) {
        return;
    }
}
