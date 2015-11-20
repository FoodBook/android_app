package tk.lenkyun.foodbook.foodbook.Client.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionRequest;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionResult;
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
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

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

    public static final String SERVICE_CREATE_POST = "post";

    public Promise<FoodPost> publishFoodPost(String caption, Location location, PhotoBundle bundle) {
        // TODO : Implement real
        if (!LoginService.getInstance().validateCurrentSession()) {
            throw new NoLoginException();
        }

        FoodPostBuilder foodPostBuilder = new FoodPostBuilder(caption, location, bundle, LoginService.getInstance().getSession());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String foodpost = mapper.writeValueAsString(foodPostBuilder);
            Promise<ConnectionResult> result = mConnection.createRequest()
                    .addServicePath(SERVICE_CREATE_POST)
                    .setDataInputParam(foodPostBuilder)
                    .addAuthentication(LoginService.getInstance().getSession())
                    .setSubmit(true)
                    .execute();

            final Promise<FoodPost> foodPostPromise = new Promise<>();
            result.onSuccess(new PromiseRun<ConnectionResult>() {
                @Override
                public void run(String status, ConnectionResult result) {
                    foodPostPromise.success("success", result.getResult(FoodPost.class));
                }
            }).onFailed(new PromiseRun<ConnectionResult>() {
                @Override
                public void run(String status, ConnectionResult result) {
                    foodPostPromise.failed(status);
                }
            });

            return foodPostPromise;
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

    Location.LatLng mLatLng = new Location.LatLng(13.5, 100);

    public Promise<NewsFeed> getNewsFeed(){
        final Promise<NewsFeed> newsFeedPromise = new Promise<>();

        ConnectionRequest connectionRequest = mConnection.createRequest();

        Promise<ConnectionResult> resultPromise = connectionRequest
                .addServicePath("feed")
                .addServicePath(String.valueOf(mLatLng.latitude)).addServicePath(String.valueOf(mLatLng.longitude))
                .addAuthentication(LoginService.getInstance().getSession())
                .execute();

        resultPromise.onSuccess(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                if(result.isError()){
                    newsFeedPromise.failed(result.getStatusDetail());
                }else{
                    FoodPost[] foodPosts = result.getResult(FoodPost[].class);
                    Arrays.sort(foodPosts);
                    NewsFeed newsFeed = new NewsFeed(UUID.randomUUID().toString());

                    for(FoodPost foodPost : foodPosts){
                        newsFeed.addFoodPost(foodPost);
                    }

                    newsFeedPromise.success("ok", newsFeed);
                }
            }
        }).bindOnFailed(newsFeedPromise);

        return newsFeedPromise;
    }
}
