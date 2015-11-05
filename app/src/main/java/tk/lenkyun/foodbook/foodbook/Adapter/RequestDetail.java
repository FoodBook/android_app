package tk.lenkyun.foodbook.foodbook.Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public interface RequestDetail {
    String getService();
    RequestDetail setService(String serviceName);
    RequestDetail addServiceParam(String name);
    RequestDetail addDetail(String name, JSONObject detail);
    RequestDetail setSubmit(boolean isSubmit);
    Promise<JSONObject> request();
    JSONObject getJSON();
    String getParams();
    boolean isSubmit();
}
