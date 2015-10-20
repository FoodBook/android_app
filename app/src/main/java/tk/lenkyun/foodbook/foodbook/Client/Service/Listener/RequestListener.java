package tk.lenkyun.foodbook.foodbook.Client.Service.Listener;

import tk.lenkyun.foodbook.foodbook.Client.Service.Exception.RequestException;

/**
 * Created by lenkyun on 20/10/2558.
 */
public interface RequestListener<E> {
    void onComplete(E result);

    void onFailed(RequestException e);
}
