package tk.lenkyun.foodbook.foodbook.Domain.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class NewsFeed {
    private String sessionId;
    private List<FoodPost> foodPostList = new LinkedList<>();

    public NewsFeed(String sessionId) {
        this.sessionId = sessionId;
    }

    public int countFoodPost(){
        return foodPostList.size();
    }

    public FoodPost getFoodPost(int index){
        return foodPostList.get(index);
    }

    public void addFoodPost(FoodPost post){
        foodPostList.add(post);
    }

    public String getSessionId() {
        return sessionId;
    }
}
