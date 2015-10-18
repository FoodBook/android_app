package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.net.Uri;

import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.AlreadyLoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.InvalidUserInfoException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.NoLoginException;
import tk.lenkyun.foodbook.foodbook.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Data.User.User;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class LoginService {
    private static LoginService instance = null;
    private static Object lock = new Object();
    private SessionAuthenticationInfo userSession = null;

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static LoginService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new LoginService();
                }
            }
        }

        return instance;
    }

    /**
     * Create new session with provided user authentication
     * @param user User authentication info
     * @return Authenticated User Info
     */
    public synchronized User login(AuthenticationInfo user) {
        // TODO : Implement real
        // Dummy
        return createDummyUserSession(user);
    }

    /**
     * Renew current session whatever it is timed-out or not.
     */
    public synchronized void updateSession(){
        // TODO : implement real
        if(userSession == null){
            synchronized (this) {
                if(userSession == null) {
                    throw new NoLoginException();
                }
            }
        }

        if(userSession.getToken().equals(DebugInfo.TOKEN)){
            AuthenticationInfo authenUser = new AuthenticationInfo(DebugInfo.USERNAME);
            authenUser.setAuthenticateInfo(DebugInfo.PASSWORD);
            userSession = null;

            login(authenUser);
        }
    }

    /**
     * Tell loginService to use existing session
     * @param session current session (no User required)
     */
    public synchronized void updateSession(SessionAuthenticationInfo session) {
        // TODO : implement real
        if(userSession.getUser().equals(DebugInfo.USERNAME) &&
                userSession.getToken().equals(DebugInfo.TOKEN)){
            this.userSession = session;
            this.updateSession();
        }
    }

    /**
     * Get current session terminated.
     */
    public synchronized void logout(){
        // TODO : Implement real
        // Dummy
        userSession = null;
    }

    /**
     * Get 'User' from current session
     * @return User in current session
     */
    public User getUser(){
        if(userSession == null){
            return null;
        }
        return userSession.getUser();
    }

    public boolean validateCurrentSession(){
        return userSession != null;

    }

    /* DEBUG */
    private User createDummyUserSession(AuthenticationInfo user) {
        if(userSession != null){
            throw new AlreadyLoginException();
        }else if(user.getUsername().equals(DebugInfo.USERNAME) && user.getAuthenticateInfo().equals(DebugInfo.PASSWORD)){
            Profile profile = user.getProfile();
            if(profile == null) {
                PhotoItem photoItem = new PhotoItem(Uri.parse(DebugInfo.PHOTO_URI));
                profile = new Profile(DebugInfo.FIRSTNAME, DebugInfo.LASTNAME, photoItem);
            }

            User userI = new User("0", DebugInfo.USERNAME, profile);
            this.userSession = new SessionAuthenticationInfo(userI, DebugInfo.TOKEN);
            return this.userSession.getUser();
        }else{
            throw new InvalidUserInfoException();
        }
    }
}
