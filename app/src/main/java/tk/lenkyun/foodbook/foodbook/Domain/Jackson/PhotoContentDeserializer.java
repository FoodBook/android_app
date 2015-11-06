package tk.lenkyun.foodbook.foodbook.Domain.Jackson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import tk.lenkyun.foodbook.foodbook.Utils.Base64Utils;

/**
 * Created by lenkyun on 6/11/2558.
 */
public class PhotoContentDeserializer extends JsonDeserializer<Bitmap> {
    @Override
    public Bitmap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return decodeBitmap(p.getText());
    }

    public Bitmap decodeBitmap(String base64){
        byte[] bytes = Base64Utils.decode(base64);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
