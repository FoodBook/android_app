package tk.lenkyun.foodbook.foodbook.Client.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.HTTPAdapter;
import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.NoLoginException;
import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.RequestListener;
import tk.lenkyun.foodbook.foodbook.Config;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.NewsFeed;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.FoodPostBuilder;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class PostFeedService {
    private static PostFeedService instance = null;
    private static Object lock = new Object();
    private Map<String, FoodPost> foodPostList = new HashMap<>();
    private int dummyInt = 0;
    private int dummyNewsfeedInt = 0;

    /**
     * Get service instance if not exists
     *
     * @return A service instance
     */
    public static PostFeedService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PostFeedService(new HTTPAdapter(Config.SERVER));
                }
            }
        }

        return instance;
    }

    ConnectionAdapter mConnection;
    public PostFeedService(ConnectionAdapter connectionAdapter){
        mConnection = connectionAdapter;
    }

    public static final String[] SERVICE_CREATE_POST = "post";
    public static final String SERVICE_ATTR = "data";

    public Promise<JSONObject> publishFoodPost(String caption, Location location, PhotoBundle bundle, RequestListener<FoodPost> requestListener) {
        // TODO : Implement real
        if (!LoginService.getInstance().validateCurrentSession()) {
            throw new NoLoginException();
        }

        FoodPostBuilder foodPostBuilder = new FoodPostBuilder(caption, location, bundle, LoginService.getInstance().getSession());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String foodpost = mapper.writeValueAsString(foodPostBuilder);
            return mConnection.createRequest()
                    .addServicePath(SERVICE_CREATE_POST)
                    .addInputParam(SERVICE_ATTR, foodpost)
                    .setSubmit(true)
                    .execute();
        } catch (JsonProcessingException e) {
            return null;
        }
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
        NewsFeed newsFeed = new NewsFeed(String.valueOf(dummyNewsfeedInt));
        for(Map.Entry<String, FoodPost> entry : foodPostList.entrySet()){
            newsFeed.addFoodPost(new FoodPost(entry.getKey(), entry.getValue().getPostDetail(), null));
        }

        return newsFeed;
    }
}
