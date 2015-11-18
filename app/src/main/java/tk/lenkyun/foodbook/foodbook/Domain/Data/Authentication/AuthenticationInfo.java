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

    public abstract String getId();
    public abstract String getInfo();
    public abstract void setId(String id);
    public abstract void setInfo(String info);

    public abstract void setAuthenticationType(String authenticateInfo);

    public String getAuthenticationType() {
        return type;
    }
}
