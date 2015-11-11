package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 17/10/2558.
 */
public interface Content<E> {
    E getContent();
    void setContent(E content);
}
