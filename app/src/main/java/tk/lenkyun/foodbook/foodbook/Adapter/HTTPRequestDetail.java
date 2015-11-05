package tk.lenkyun.foodbook.foodbook.Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class HTTPRequestDetail implements RequestDetail {
    private HTTPAdapter mHttpAdapter;
    public HTTPRequestDetail(HTTPAdapter httpAdapter){
        mHttpAdapter = httpAdapter;
    }

    String mService = ServiceName.DEFAULT;

    public static String SERVICE_OPT = "service";

    public boolean isSubmit() {
        return submit;
    }

    public static class ServiceName {
        public static final String DEFAULT = "";
        public static final String UPLOAD = "upload";
        public static final String NEWSFEED = "newsfeed";
    }

    public String getService(){
        return mService;
    }

    public HTTPRequestDetail setService(String serviceName){
        mService = serviceName;
        return this;
    }

    private String params = "";
    @Override
    public RequestDetail addServiceParam(String name) {
        params += name;
        return this;
    }

    HashMap<String, JSONObject> details = new HashMap<>();
    public HTTPRequestDetail addDetail(String name, JSONObject foodbookType){
        details.put(name, foodbookType);
        return this;
    }

    @Override
    public Promise<JSONObject> request(){
        return mHttpAdapter.postRequest(this);
    }

    @Override
    public JSONObject getJSON(){
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String, JSONObject> map : details.entrySet()){
            try {
                jsonObject.put(map.getKey(), map.getValue());
            } catch (JSONException e) {
            }
        }

        return jsonObject;
    }

    @Override
    public String getParams() {
        return params;
    }

    private boolean submit = false;
    @Override
    public RequestDetail setSubmit(boolean isSubmit) {
        submit = isSubmit;
        return this;
    }
}
