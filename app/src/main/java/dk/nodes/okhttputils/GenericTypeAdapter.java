package dk.nodes.okhttputils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
/**
 * Created by joso on 13/05/16.
 */

public class GenericTypeAdapter<T> implements JsonDeserializer<T> {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Class<?> c = (Class<?>) type;
        T t = null;
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.get("data").isJsonObject()) {
            JsonObject data = jsonObject.getAsJsonObject("data");
            t = (T) gson.fromJson(data.toString(), c);

            return t;
        } else if (jsonObject.get("data").isJsonArray()) {
            t = (T) gson.fromJson(jsonObject.toString(), c);

            return t;
        }

        return null;
    }

}
