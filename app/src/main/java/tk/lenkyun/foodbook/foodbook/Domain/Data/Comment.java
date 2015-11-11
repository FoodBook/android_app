package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Comment implements FoodbookType {
    private String id, message;
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
}
