package tk.lenkyun.foodbook.foodbook.Domain.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class FoodPostDetail {
    private Date createdDate = null;
    private List<Tag> tagList = new LinkedList<>();
    private Location location;
    private List<PhotoItem> photoItems = new LinkedList<PhotoItem>();
    private String caption;

    public FoodPostDetail(String caption, Location location) {
        this.location = location;
    }

    public void addPhoto(PhotoItem photoItem) {
        photoItems.add(photoItem);
    }

    public int countPhoto(){
        return photoItems.size();
    }

    public PhotoItem getPhoto(int index) {
        return photoItems.get(index);
    }

    public int countTag(){
        return tagList.size();
    }

    public Tag getTag(int index){
        return tagList.get(index);
    }

    public void pushTag(Tag tag){
        tagList.add(tag);
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
}
