package tk.lenkyun.foodbook.foodbook.Client.Service;

import java.util.HashMap;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Data.NewsFeed;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class NewsFeedService {
    private Map<String, FoodPost> foodPostList = new HashMap<>();

    private static NewsFeedService instance = null;
    private static Object lock = new Object();

    private int dummyInt = 0;
    public void publishFoodPost(FoodPost foodPost){
        // TODO : Implement real
        foodPost.setId(String.valueOf(dummyInt));
        foodPostList.put(String.valueOf(dummyInt++), foodPost);
    }

    public FoodPost getFeedIndex(NewsFeed feed, int index){
        // TODO : Implement real
        String postId = feed.getFoodPost(index).getId();
        if(foodPostList.containsKey(postId)){
            return foodPostList.get(postId);
        }else{
            return null;
        }
    }

    public NewsFeed getNewsFeed(){
        // TODO : Implement real
        // Now dummy news feed
        NewsFeed newsFeed = new NewsFeed("0110");
        for(Map.Entry<String, FoodPost> entry : foodPostList.entrySet()){
            newsFeed.addFoodPost(new FoodPost(entry.getKey(), entry.getValue().getPostDetail(), null));
        }

        return newsFeed;
    }

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static NewsFeedService getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new NewsFeedService();
                }
            }
        }

        return instance;
    }
}
