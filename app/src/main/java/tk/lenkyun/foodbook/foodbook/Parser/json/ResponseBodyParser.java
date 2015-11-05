package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONException;
import org.json.JSONObject;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Body.ResponseDesc;

/**
 * Created by lenkyun on 5/11/2558.
 */
public class ResponseBodyParser extends JSONParser<ResponseDesc> {

    @Override
    public JSONObject parse(ResponseDesc object) {
        JSONObject json = new JSONObject();
        try {
            json.put("error", object.getError());
            json.put("status", object.getStatus());

            if(object.getData() != null)
                json.put("data", object.getData());
        } catch (JSONException e) {
            return null;
        }

        return json;
    }

    @Override
    public ResponseDesc from(JSONObject json) {
        ResponseDesc responseDesc = new ResponseDesc();

        try {
            responseDesc.setError(json.getInt("error"));
            responseDesc.setStatus(json.getString("status"));
            if(json.has("data")){
                responseDesc.setData(json.getJSONObject("data"));
            }
        } catch (JSONException e) {
            return null;
        }

        return responseDesc;
    }
}
