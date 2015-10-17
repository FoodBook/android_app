package tk.lenkyun.foodbook.foodbook.Client.Service.Listener;

import tk.lenkyun.foodbook.foodbook.Data.Content;

/**
 * Created by lenkyun on 17/10/2558.
 */
public interface DataListener<E> {
    void onLoaded(E content);
    void onFailed(String errorDetail);
}
