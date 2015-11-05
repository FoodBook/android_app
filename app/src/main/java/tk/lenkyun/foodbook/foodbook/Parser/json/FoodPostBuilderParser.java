package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Location;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.FoodPostBuilder;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class FoodPostBuilderParser extends JSONParser<FoodPostBuilder> {
    @Override
    public JSONObject parse(FoodPostBuilder object) {
        JSONObject json = new JSONObject();
        try {
            if(object.getLocation() != null)
                json.put("location", new LocationParser().parse(object.getLocation()));

            json.put("caption", object.getCaption());
            json.put("bundle", new PhotoBundleParser().parse(object.getBundle()));
            json.put("user", new AuthenticationInfoParser().parse(object.getOwner()));

            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public FoodPostBuilder from(JSONObject json) {
        try {
            Location location = null;
            if(json.has("location"))
                location = new LocationParser().from(json.getJSONObject("location"));

            String caption = json.getString("caption");
            PhotoBundle bundle = new PhotoBundleParser().from(json.getJSONObject("bundle"));
            AuthenticationInfo authenticationInfo = new AuthenticationInfoParser().from(json.getJSONObject("user"));

            return new FoodPostBuilder(caption, location, bundle, authenticationInfo);
        } catch (JSONException e) {
            return null;
        }
    }
}
