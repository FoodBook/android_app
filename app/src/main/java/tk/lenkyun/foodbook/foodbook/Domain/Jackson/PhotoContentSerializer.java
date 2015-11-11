package tk.lenkyun.foodbook.foodbook.Domain.Jackson;

import android.graphics.Bitmap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tk.lenkyun.foodbook.foodbook.Utils.Base64Utils;

/**
 * Created by lenkyun on 6/11/2558.
 */
public class PhotoContentSerializer extends JsonSerializer<Bitmap> {
    @Override
    public void serialize(Bitmap value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        value.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        gen.writeString(Base64Utils.encode(bytes.toByteArray()));
    }
}
