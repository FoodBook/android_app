package tk.lenkyun.foodbook.foodbook.Client.Service;

import tk.lenkyun.foodbook.foodbook.Data.User.AuthenUserFacebook;
import tk.lenkyun.foodbook.foodbook.Data.User.AuthenUser;
import tk.lenkyun.foodbook.foodbook.Data.User.UserSession;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class RegisterService {

    public UserSession register(AuthenUser authenUser){
        // TODO : Implement real
        return null;
    }

    public UserSession registerFacebook(AuthenUserFacebook authenUser){
        // TODO : Implement real
        return null;
    }

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

}
