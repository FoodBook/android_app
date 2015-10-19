package tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

/**
 * Created by lenkyun on 15/10/2558.
 */
public abstract class AuthenticationInfo implements FoodbookType {
    private String type;

    public AuthenticationInfo(String type) {
        this.type = type;
    }

    public abstract String getAuthenticateId();
    public abstract String getAuthenticateInfo();

    public abstract void setAuthenticateInfo(String authenticateInfo);

    public String getAuthenticationType() {
        return type;
    }
}
