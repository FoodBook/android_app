package tk.lenkyun.foodbook.foodbook.Domain.Data.User;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class User implements FoodbookType {
    private String id, username;
    private Profile profile;

    public User(String id, String username, Profile profile){
        this.id = id;
        this.username = username;
        this.profile = profile;
    }

    public String getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public Profile getProfile(){
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }
}
