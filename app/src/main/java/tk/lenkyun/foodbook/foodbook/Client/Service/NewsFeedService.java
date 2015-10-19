package tk.lenkyun.foodbook.foodbook.Client.Service;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.NoLoginException;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPost;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodPostDetail;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Data.NewsFeed;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.FoodPostBuilder;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class NewsFeedService {
    private static NewsFeedService instance = null;
    private static Object lock = new Object();
    private Map<String, FoodPost> foodPostList = new HashMap<>();
    private int dummyInt = 0;

    /**
     * Get service instance if not exists
     *
     * @return A service instance
     */
    public static NewsFeedService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new NewsFeedService();
                }
            }
        }

        return instance;
    }

    public void publishFoodPost(String caption, Location location, PhotoBundle bundle) {
        // TODO : Implement real
        if (!LoginService.getInstance().validateCurrentSession()) {
            throw new NoLoginException();
        }

        FoodPostBuilder foodPostBuilder = new FoodPostBuilder(caption, location, bundle, LoginService.getInstance().getUser());

        // mock server
        FoodPostDetail detail = new FoodPostDetail(foodPostBuilder.getCaption(), foodPostBuilder.getLocation());

        PhotoItem c;
        for (PhotoContent<Bitmap> photoContent : foodPostBuilder.getBundle()) {
            c = PhotoContentService.getInstance().mockAddPhoto(photoContent);
            detail.addPhoto(c);
        }

        FoodPost foodPost = new FoodPost(String.valueOf(dummyInt), detail, foodPostBuilder.getOwner());
        foodPost.setId(String.valueOf(dummyInt));
        for (PhotoContent<Bitmap> photoContent : bundle) {

        }
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
}
