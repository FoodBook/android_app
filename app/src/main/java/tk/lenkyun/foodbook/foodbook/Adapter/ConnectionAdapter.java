package tk.lenkyun.foodbook.foodbook.Adapter;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public interface ConnectionAdapter<RDetail extends ConnectionRequest> {
    RDetail createRequest();
    Promise<ConnectionResult> postRequest(RDetail requestDetail);
}
