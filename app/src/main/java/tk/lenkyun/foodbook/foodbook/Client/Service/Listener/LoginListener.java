package tk.lenkyun.foodbook.foodbook.Client.Service.Listener;

import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.LoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;

/**
 * Created by lenkyun on 20/10/2558.
 */
public interface LoginListener {
    void onLoginSuccess(LoginService loginService, User user);

    void onLoginFailed(LoginService loginService, AuthenticationInfo authenticationInfo, LoginException login);

    void onLogout(LoginService loginService);
}
