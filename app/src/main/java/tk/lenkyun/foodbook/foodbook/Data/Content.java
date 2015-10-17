package tk.lenkyun.foodbook.foodbook.Data;

/**
 * Created by lenkyun on 17/10/2558.
 */
public interface Content<E> {
    String getContentType();

    E getContent();
    void setContent(E content);
}
