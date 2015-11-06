package tk.lenkyun.foodbook.foodbook.Domain.Jackson;

import android.net.Uri;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by lenkyun on 6/11/2558.
 */
public class UriSerializer extends JsonSerializer<Uri> {
    @Override
    public void serialize(Uri value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(value.toString());
    }
}
