package tk.lenkyun.foodbook.foodbook.Adapter;

import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public interface ConnectionAdapter<RDetail extends RequestDetail> {
    RDetail createRequest();
    Promise<JSONObject> postRequest(RDetail requestDetail);
}
