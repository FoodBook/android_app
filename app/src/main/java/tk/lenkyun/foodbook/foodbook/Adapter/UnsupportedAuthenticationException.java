package tk.lenkyun.foodbook.foodbook.Adapter;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;

/**
 * Created by lenkyun on 7/11/2558.
 */
public class UnsupportedAuthenticationException extends RuntimeException {
    public UnsupportedAuthenticationException(AuthenticationInfo authenticationInfo){
        super("Authentication type " + authenticationInfo.getAuthenticationType() + " is not supported.");
    }
}
