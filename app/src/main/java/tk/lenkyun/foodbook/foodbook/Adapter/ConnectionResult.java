package tk.lenkyun.foodbook.foodbook.Adapter;

import org.json.JSONObject;

/**
 * Created by lenkyun on 7/11/2558.
 */
public interface ConnectionResult {
    boolean isError();
    boolean isConnectionError();
    long getErrorCode();
    String getStatusDetail();
    <E> E getResult(Class<E> c);
}
