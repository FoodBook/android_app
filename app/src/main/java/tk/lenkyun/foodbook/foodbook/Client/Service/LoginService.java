package tk.lenkyun.foodbook.foodbook.Client.Service;

import org.apache.commons.collections4.map.LRUMap;

import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionResult;
import tk.lenkyun.foodbook.foodbook.Adapter.HTTPAdapter;
import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.LoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.NoLoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.LoginListener;
import tk.lenkyun.foodbook.foodbook.Config;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class LoginService {
    private static LoginService instance = null;
    private static Object lock = new Object();
    private SessionAuthenticationInfo userSession = null;
    private User loggedInUser = null;
    private LoginListenerList loginListeners;

    private ConnectionAdapter mConnectionAdapter;
    public LoginService(ConnectionAdapter connectionAdapter) {
        loginListeners = new LoginListenerList(this);
        mConnectionAdapter = connectionAdapter;
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
                    instance = new LoginService(new HTTPAdapter(Config.SERVER));
                }
            }
        }

        return instance;
    }

    /**
     * Create new session with provided user authentication
     * @param user User authentication info
     * @return result promise
     */
    public Promise<User> login(UserAuthenticationInfo user) {
        final Promise<User> promise = new Promise<>();
        final Promise<ConnectionResult> resultPromise = mConnectionAdapter.createRequest()
                .addServicePath("oauth").addServicePath("login")
                .setSubmit(true)
                .setDataInputParam(user)
                .execute();

        resultPromise.onSuccess(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                if (!result.isError()) {
                    userSession = result.getResult(SessionAuthenticationInfo.class);
                    getUser().bind(promise);
                } else {
                    userSession = null;
                    promise.failed("login failed");
                }
            }
        });

        return promise;
    }

    /**
     * Get 'User' from current session
     * @return User in current session
     */
    public Promise<User> getUser(){
        final Promise<User> promise = new Promise<>();

        if(userSession == null){
            promise.failed("no user logged in");
            return promise;
        }

        Promise<ConnectionResult> resultPromise = mConnectionAdapter.createRequest()
                .addServicePath("user").addServicePath("me")
                .addAuthentication(getSession())
                .execute();

        resultPromise.onSuccess(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                if(result.isError())
                    promise.failed(status);
                else
                    promise.success("success", result.getResult(User.class));
            }
        });

        resultPromise.bindOnFailed(promise);
        return promise;
    }

    public SessionAuthenticationInfo getSession(){
        return userSession;
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

        if (userSession.getInfo().equals(DebugInfo.TOKEN)) {
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
        if (userSession.getId().equals(DebugInfo.UID) &&
                userSession.getInfo().equals(DebugInfo.TOKEN)) {
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

    public boolean validateCurrentSession(){
        return userSession != null;

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
