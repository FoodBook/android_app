package tk.lenkyun.foodbook.foodbook.Domain.Data;

import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class FoodPost implements FoodbookType {
    private String id;
    private List<Comment> commentList = new LinkedList<>();
    private FoodPostDetail postDetail;
    private User owner;
    private float averageRate = 0;

    public FoodPost(){};
    public FoodPost(String id, FoodPostDetail postDetail, User owner) {
        this.id = id;
        this.postDetail = postDetail;
        this.owner = owner;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public void addComment(int index, Comment comment){
        commentList.add(index, comment);
    }

    public void deleteComment(int index){
        commentList.remove(index);
    }

    public Comment getComment(int index){
        if(index >= commentList.size()) {
            return null;
        }
        return commentList.get(index);
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner){
        this.owner = owner;
    }

    public FoodPostDetail getPostDetail() {
        return postDetail;
    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }
}
