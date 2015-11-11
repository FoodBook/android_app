package tk.lenkyun.foodbook.foodbook.Client.Helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Repository {
    Map<String, Object> objects = new HashMap<>();
    public Object getData(String key){
        return objects.get(key);
    }

    public void setData(String key, Object object){
        objects.put(key, object);
    }

    private static Repository instance = null;
    private static Object lock = new Object();

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static Repository getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new Repository();
                }
            }
        }

        return instance;
    }
}
