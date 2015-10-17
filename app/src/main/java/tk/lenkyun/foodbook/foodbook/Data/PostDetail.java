package tk.lenkyun.foodbook.foodbook.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class PostDetail {
    private Date createdDate = null;
    private List<Tag> tagList = new LinkedList<>();
    private Location location;
    private List<Photo> photos = new LinkedList<Photo>();
    private String caption;

    public PostDetail(String caption, Location location){
        this.location = location;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public int countPhoto(){
        return photos.size();
    }

    public Photo getPhoto(int index){
        return photos.get(index);
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
