package tk.lenkyun.foodbook.foodbook.Data.User;

import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class Profile {
    private String firstname = null, lastname = null;
    private Photo profilePicture = null;
    private Photo coverPicture = null;

    public Profile(){};
    public Profile(String firstname, String lastname, Photo photo){
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePicture = photo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Photo getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Photo profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Photo getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(Photo coverPicture) {
        this.coverPicture = coverPicture;
    }
}
