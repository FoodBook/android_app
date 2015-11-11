package tk.lenkyun.foodbook.foodbook.Domain.Jackson;

import android.net.Uri;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by lenkyun on 6/11/2558.
 */
public class UriDeserializer extends JsonDeserializer<Uri> {
    @Override
    public Uri deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Uri.parse(p.getText());
    }
}
