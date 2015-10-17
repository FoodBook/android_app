package tk.lenkyun.foodbook.foodbook.Data.User;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class UserSession {
    private User user;
    private String token;

    public UserSession(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
