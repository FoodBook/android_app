package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

/**
 * Created by lenkyun on 4/11/2558.
 */
public abstract class JSONParser<E extends FoodbookType> {
    public abstract JSONObject parse(E object);
    public abstract E from(JSONObject json);
}
