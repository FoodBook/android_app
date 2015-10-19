package tk.lenkyun.foodbook.foodbook.Domain.Operation;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;

/**
 * Created by lenkyun on 19/10/2558.
 */
public class FoodPostBuilder {
    private Location location;
    private String caption;
    private PhotoBundle bundle;
    private User owner;

    public FoodPostBuilder(String caption, Location location, PhotoBundle photos, User owner) {
        this.location = location;
        this.caption = caption;
        this.bundle = photos;
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public PhotoBundle getBundle() {
        return bundle;
    }

    public void setBundle(PhotoBundle bundle) {
        this.bundle = bundle;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
