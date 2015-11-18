package tk.lenkyun.foodbook.foodbook.Domain.Data;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class FoodPostDetail {
    private Date createdDate = null;
    @JsonProperty("tag")
    private List<Tag> tagList = new LinkedList<>();
    private Location location;
    @JsonProperty("photos")
    private List<PhotoItem> photoItems = new LinkedList<PhotoItem>();
    private String caption;

    public FoodPostDetail(String caption, Location location) {
        this.caption = caption;
        this.location = location;
    }

    public void addPhoto(PhotoItem photoItem) {
        photoItems.add(photoItem);
    }

    public int countPhoto(){
        return photoItems.size();
    }

    @JsonIgnore
    public PhotoItem getPhoto(int index) {
        return photoItems.get(index);
    }

    public List<PhotoItem> getPhotoItems(){
        return photoItems;
    }

    public void setPhotoItems(List<PhotoItem> photoItems){
        this.photoItems = photoItems;
    }

    public int countTag(){
        return tagList.size();
    }

    public Tag getTag(int index){
        return tagList.get(index);
    }

    @JsonIgnore
    public Collection<Tag> getTags(){
        return tagList;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
