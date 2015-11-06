package tk.lenkyun.foodbook.foodbook.Adapter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by lenkyun on 7/11/2558.
 */
public class HTTPResult implements ConnectionResult {
    JSONObject json;
    public HTTPResult(String jsonString) throws JSONException {
        json = new JSONObject(jsonString);
    }

    @Override
    public boolean isError() {
        try {
            if(json.has("status"))
                return false;
            else if(json.has("error") && json.getLong("error") == 0)
                return true;
        } catch (JSONException ignored) {}

        return false;
    }

    @Override
    public boolean isConnectionError() {
        if(json.has("status"))
            return true;
        return false;
    }

    @Override
    public long getErrorCode() {
        try {
            if (json.has("status"))
                return json.getLong("status");
            else if (json.has("error"))
                return json.getLong("error");
        } catch (JSONException ignored) {}
        return 0;
    }

    @Override
    public String getStatusDetail() {
        try {
            if (json.has("status"))
                return json.getString("error");
            else if (json.has("detail")) {
                return json.getString("detail");
            }
        } catch (JSONException ignored) {}

        return null;
    }

    @Override
    public <E> E getResult(Class<E> c) {
        if(!json.has("result"))
            return null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json.getJSONObject("result").toString(), c);
        } catch (JSONException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (JsonParseException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
