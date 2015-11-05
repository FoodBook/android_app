package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.PhotoBundle;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class PhotoBundleParser extends JSONParser<PhotoBundle> {
    @Override
    public JSONObject parse(PhotoBundle object) {
        JSONObject json = new JSONObject();
        JSONArray jarray = new JSONArray();

        PhotoContentParser parser = new PhotoContentParser();
        for(PhotoContent photoContent : object){
            jarray.put(parser.parse(photoContent));
        }

        try {
            json.put("list", jarray);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public PhotoBundle from(JSONObject json) {
        PhotoBundle bundle = new PhotoBundle();
        try {
            JSONArray array = json.getJSONArray("list");
            PhotoContentParser contentParser = new PhotoContentParser();
            for(int i = 0; i < array.length(); i++) {
                bundle.put(contentParser.from(array.getJSONObject(i)));
            }

            return bundle;
        } catch (JSONException e) {
            return null;
        }
    }
}
