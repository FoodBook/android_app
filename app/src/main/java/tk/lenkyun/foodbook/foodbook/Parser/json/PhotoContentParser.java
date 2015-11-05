package tk.lenkyun.foodbook.foodbook.Parser.json;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Photo.PhotoContent;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class PhotoContentParser extends JSONParser<PhotoContent> {
    @Override
    public JSONObject parse(PhotoContent object) {
        JSONObject json = new JSONObject();

        try {
            json.put("content", parse(object.getContent()));
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public PhotoContent from(JSONObject json) {
        try {
            return new PhotoContent(decodeBitmap(json.getString("content")));
        } catch (JSONException e) {
            return null;
        }
    }

    public Bitmap decodeBitmap(String base64){
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public String parse(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Base64.encodeToString(bytes.toByteArray(), Base64.NO_WRAP);
    }
}
