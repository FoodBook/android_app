package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class UserAuthenticationInfo extends AuthenticationInfo {
    public static final String AUTH_TYPE = "username/password";
    private String password;
    private String userid;

    public UserAuthenticationInfo(){
        super(AUTH_TYPE);
    }

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
    public void setId(String id) {
        userid = id;
    }

    @Override
    public void setInfo(String info) {
        password = info;
    }

    @Override
    public void setAuthenticationType(String authenticateInfo) {
        return;
    }
}
