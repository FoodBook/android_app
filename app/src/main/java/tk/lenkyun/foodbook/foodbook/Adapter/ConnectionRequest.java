package tk.lenkyun.foodbook.foodbook.Adapter;

import org.json.JSONObject;

import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public interface ConnectionRequest {
    String[] getServicePaths();
    String[] getServiceParams();
    SessionAuthenticationInfo getAuthenticationInfo();

    JSONObject getInputJSON();
    Map<String, Object> getInputMap();

    ConnectionRequest addServicePath(String serviceName);
    ConnectionRequest addServiceParam(String name);
    ConnectionRequest setDataInputParam(Object detail);
    ConnectionRequest addInputParam(String name, Object detail);
    ConnectionRequest addAuthentication(SessionAuthenticationInfo authenticationInfo);
    ConnectionRequest setSubmit(boolean isSubmit);
    Promise<ConnectionResult> execute();
    boolean isSubmit();
}
