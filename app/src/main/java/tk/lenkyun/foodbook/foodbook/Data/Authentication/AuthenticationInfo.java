package tk.lenkyun.foodbook.foodbook.Data.Authentication;

import tk.lenkyun.foodbook.foodbook.Data.FoodbookType;

/**
 * Created by lenkyun on 15/10/2558.
 */
public abstract class AuthenticationInfo implements FoodbookType {
    private String type;

    public AuthenticationInfo(String type) {
        this.type = type;
    }

    public abstract String getAuthenticateInfo();

    public abstract void setAuthenticateInfo(String authenticateInfo);

    public String getAuthenticationType() {
        return type;
    }
}
