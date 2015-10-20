package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.net.Uri;

import org.apache.commons.collections4.map.LRUMap;

import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.AlreadyLoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.InvalidUserInfoException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.LoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.NoLoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.LoginListener;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class LoginService {
    private static LoginService instance = null;
    private static Object lock = new Object();
    private SessionAuthenticationInfo userSession = null;
    private User loggedInUser = null;
    private LoginListenerList loginListeners;

    public LoginService() {
        loginListeners = new LoginListenerList(this);
    }

    /**
     * Get service instance if not exists
     *
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
     */
    public void login(UserAuthenticationInfo user) {
        // TODO : Implement real
        // Dummy
        createDummyUserSession(user);
    }

    /**
     * Renew current session whatever it is timed-out or not.
     */
    public synchronized void updateSession() {
        // TODO : implement real
        if (userSession == null) {
            synchronized (this) {
                if (userSession == null) {
                    throw new NoLoginException();
                }
            }
        }

        if (userSession.getAuthenticateInfo().equals(DebugInfo.TOKEN)) {
            UserAuthenticationInfo authenUser = new UserAuthenticationInfo(DebugInfo.USERNAME, DebugInfo.PASSWORD);
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
        if (userSession.getUserId().equals(DebugInfo.UID) &&
                userSession.getAuthenticateInfo().equals(DebugInfo.TOKEN)) {
            this.userSession = session;
            this.updateSession();
        }
    }

    /**
     * Get current session terminated.
     */
    public synchronized void logout() {
        // TODO : Implement real
        // Dummy
        userSession = null;
    }

    /**
     * Get 'User' from current session
     * @return User in current session
     */
    public User getUser() {
        if (loggedInUser == null)
            return null;

        if (userSession == null || !userSession.getUserId().equals(loggedInUser.getId())) {
            return null;
        }
        return loggedInUser;
    }

    public boolean validateCurrentSession(){
        return userSession != null;

    }

    /* DEBUG */
    private void createDummyUserSession(UserAuthenticationInfo user) {
        if(userSession != null) {
            loginListeners.onLoginFailed(user, new AlreadyLoginException());
        } else if (user.getAuthenticateId().equals(DebugInfo.USERNAME) && user.getAuthenticateInfo().equals(DebugInfo.PASSWORD)) {
            Profile profile = null;
            if(profile == null) {
                PhotoItem photoItem = new PhotoItem(Uri.parse(DebugInfo.PHOTO_URI), 300, 300);
                profile = new Profile(DebugInfo.FIRSTNAME, DebugInfo.LASTNAME, photoItem);
            }

            User userI = new User(DebugInfo.UID, DebugInfo.USERNAME, profile);
            this.userSession = new SessionAuthenticationInfo(userI.getId(), userI.getUsername(), DebugInfo.TOKEN);
            this.loggedInUser = userI;
            loginListeners.onLoginSuccess(loggedInUser);
        } else {
            loginListeners.onLoginFailed(user, new InvalidUserInfoException());
        }
    }

    public void addLoginListener(Class key, LoginListener loginListener) {
        this.loginListeners.put(key, loginListener);
    }

    public void removeLoginListener(Class key) {
        this.loginListeners.remove(key);
    }

    private class LoginListenerList extends LRUMap<Class, LoginListener> {
        private LoginService loginService;

        public LoginListenerList(LoginService loginService) {
            this.loginService = loginService;
        }

        void onLoginSuccess(User user) {
            for (Entry<Class, LoginListener> l : this.entrySet()) {
                if (l != null)
                    l.getValue().onLoginSuccess(loginService, user);
            }
        }

        void onLoginFailed(AuthenticationInfo authenticationInfo, LoginException loginException) {
            for (Entry<Class, LoginListener> l : this.entrySet()) {
                if (l != null)
                    l.getValue().onLoginFailed(loginService, authenticationInfo, loginException);
            }
        }

        void onLogout() {
            for (Entry<Class, LoginListener> l : this.entrySet()) {
                if (l != null)
                    l.getValue().onLogout(loginService);
            }
        }
    }
}
