package tk.lenkyun.foodbook.foodbook.Domain.Data;

import tk.lenkyun.foodbook.foodbook.Domain.Data.User.User;

import java.util.Date;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Comment implements FoodbookType {
    private String id, message;
    private User user;
    private FoodPost assoc;
    private Date createdDate;

    public Comment(){}
    public Comment(String id, String message){
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public FoodPost getAssoc() {
        return assoc;
    }

    public void setAssoc(FoodPost assoc) {
        this.assoc = assoc;
    }
}
