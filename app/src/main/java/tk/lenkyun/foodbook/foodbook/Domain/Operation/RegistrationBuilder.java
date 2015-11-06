package tk.lenkyun.foodbook.foodbook.Domain.Operation;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;

/**
 * Created by lenkyun on 5/11/2558.
 */
public class RegistrationBuilder implements FoodbookType {
    // User
    private UserAuthenticationInfo authenticationInfo;
    private String username;
    // Profile
    private String firstname, lastname;
    private PhotoContent profilePicture;
    private PhotoContent coverPicture;
    private String facebookToken;

    public UserAuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public RegistrationBuilder setAuthenticationInfo(UserAuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RegistrationBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public RegistrationBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public RegistrationBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public PhotoContent getProfilePicture() {
        return profilePicture;
    }

    public RegistrationBuilder setProfilePicture(PhotoContent profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public PhotoContent getCoverPicture() {
        return coverPicture;
    }

    public RegistrationBuilder setCoverPicture(PhotoContent coverPicture) {
        this.coverPicture = coverPicture;
        return this;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }
}
