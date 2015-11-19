package tk.lenkyun.foodbook.foodbook.Adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class HTTPRequest implements ConnectionRequest {
    private HTTPAdapter mHttpAdapter;
    public HTTPRequest(HTTPAdapter httpAdapter){
        mHttpAdapter = httpAdapter;
        addServicePath(ServiceName.DEFAULT);
    }

    private ArrayList<String> mService = new ArrayList<>();

    public static String SERVICE_OPT = "service";

    public boolean isSubmit() {
        return submit;
    }

    public static class ServiceName {
        public static final String DEFAULT = "";
        public static final String UPLOAD = "upload";
        public static final String NEWSFEED = "newsfeed";
    }

    public String[] getServicePaths(){
        return mService.toArray(new String[mService.size()]);
    }

    public HTTPRequest addServicePath(String serviceName){
        mService.add(serviceName);
        return this;
    }

    private ArrayList<String> params = new ArrayList<>();
    @Override
    @Deprecated
    public HTTPRequest addServiceParam(String name) {
        params.add(name);
        return this;
    }

    @Override
    public ConnectionRequest setDataInputParam(Object detail) {
        addInputParam("data", detail);
        return this;
    }

    HashMap<String, JSONObject> details = new HashMap<>();
    @Override
    public HTTPRequest addInputParam(String name, Object input){
        try {
            ObjectMapper mapper = new ObjectMapper();
            details.put(name, new JSONObject(mapper.writeValueAsString(input)));
        } catch (JSONException | JsonProcessingException ignored) {}
        return this;
    }

    SessionAuthenticationInfo session = null;
    @Override
    public HTTPRequest addAuthentication(SessionAuthenticationInfo authenticationInfo) {
        session = (SessionAuthenticationInfo) authenticationInfo;
        return this;
    }

    public String getToken(){
        if(session != null){
            return session.getInfo();
        }
        return null;
    }

    @Override
    public Promise<ConnectionResult> execute(){
        return mHttpAdapter.postRequest(this);
    }

    @Override
    public JSONObject getInputJSON(){
        return details.get("data");
    }

    @Override
    public Map<String, Object> getInputMap() {
        return (Map<String, Object>) details.clone();
    }

    @Override
    public String[] getServiceParams() {
        return params.toArray(new String[params.size()]);
    }

    @Override
    public SessionAuthenticationInfo getAuthenticationInfo() {
        return session;
    }

    private boolean submit = false;
    @Override
    public HTTPRequest setSubmit(boolean isSubmit) {
        submit = isSubmit;
        return this;
    }
}
