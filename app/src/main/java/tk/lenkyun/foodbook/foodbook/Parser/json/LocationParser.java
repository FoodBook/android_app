package tk.lenkyun.foodbook.foodbook.Parser.json;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class LocationParser extends JSONParser<Location> {
    @Override
    public JSONObject parse(Location object) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", object.getName());

            if(object.getCoordinate() != null)
                json.put("coordiante", parse(object.getCoordinate()));

            if(object.getRestaurant() != null)
                json.put("restaurant", new RestaurantParser().parse(object.getRestaurant()));

            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public Location from(JSONObject json) {
        try {
            LatLng latLng = null;
            if(json.has("coordinate"))
                latLng = latLngFrom(json.getJSONObject("coordinate"));

            Location location = new Location(json.getString("name"),
                    latLng,
                    new RestaurantParser().from(json.getJSONObject("restaurant")));
            return location;
        } catch (JSONException e) {
            return null;
        }
    }

    public LatLng latLngFrom(JSONObject json){
        try {
            return new LatLng(json.getDouble("latitude"), json.getDouble("longitude"));
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject parse(LatLng latLng){
        JSONObject json = new JSONObject();

        try {
            json.put("latitude", latLng.latitude);
            json.put("longitude", latLng.longitude);

            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
