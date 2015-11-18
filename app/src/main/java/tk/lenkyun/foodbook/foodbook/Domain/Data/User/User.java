package tk.lenkyun.foodbook.foodbook.Domain.Data.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class User implements FoodbookType {
    private String id, username;
    private Profile profile;
    private UserAuthenticationInfo userAuthenticationInfo;
    private String socialId;
    @JsonIgnore
    private Collection<User> following = new ArrayList<>();

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

    public UserAuthenticationInfo getUserAuthenticationInfo() {
        return userAuthenticationInfo;
    }

    public void setUserAuthenticationInfo(UserAuthenticationInfo userAuthenticationInfo) {
        this.userAuthenticationInfo = userAuthenticationInfo;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public Collection<User> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<User> following) {
        this.following = following;
    }
}
