package tk.lenkyun.foodbook.foodbook.Data.User;

import tk.lenkyun.foodbook.foodbook.Data.User.Profile;
import tk.lenkyun.foodbook.foodbook.Data.User.User;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class AuthenUser extends User {
    public static final String AUTHEN_UID = "-2";
    private String authenticateInfo;

    public AuthenUser(String username) {
        super(AUTHEN_UID, username, null);
    }

    public void setAuthenticateInfo(String authenticateInfo){
        this.authenticateInfo = authenticateInfo;
    }

    public String getAuthenticateInfo() {
        return authenticateInfo;
    }

    public String getAuthenticationType(){
        return "username/password";
    }
}
