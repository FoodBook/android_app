package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

public class FacebookAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "facebook/token";
    private String facebookToken;
    private String facebookId;

    public FacebookAuthenticationInfo(){
        super(AUTH_TYPE);
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
    public void setId(String id) {
        facebookId = id;
    }

    @Override
    public void setInfo(String info) {
        facebookToken = info;
    }

    @Override
    public void setAuthenticationType(String authenticateInfo) {
        //throw new UnsupportedOperationException("No support for FacebookAuthenticationInfo to set new token. (Must request from Tools)");
    }
}
