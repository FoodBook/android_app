package tk.lenkyun.foodbook.foodbook.Client.Service.Listener;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Content;

/**
 * Created by lenkyun on 15/10/2558.
 */
public interface ContentListener<E> {
    void onContentLoaded(Content<E> content);
    void onContentFailed(String errorDetail);
}
