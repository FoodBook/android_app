package tk.lenkyun.foodbook.foodbook.Client.Service;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.FacebookAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class RegisterService {

    private static RegisterService instance = null;
    private static Object lock = new Object();

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static RegisterService getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new RegisterService();
                }
            }
        }

        return instance;
    }

    public SessionAuthenticationInfo register(AuthenticationInfo authenUser) {
        // TODO : Implement real
        return null;
    }

    public SessionAuthenticationInfo registerFacebook(FacebookAuthenticationInfo authenUser) {
        // TODO : Implement real
        return null;
    }

}
