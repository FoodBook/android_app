package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Restaurant;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class RestaurantParser extends JSONParser<Restaurant> {
    @Override
    public JSONObject parse(Restaurant object) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", object.getName());

            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public Restaurant from(JSONObject json) {
        try {
            return new Restaurant(json.getString("name"));
        } catch (JSONException e) {
            return null;
        }
    }
}
