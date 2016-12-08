package dk.nodes.okhttputils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
/**
 * Created by joso on 29/11/2016.
 * Implementation credits: https://github.com/nickbutcher/plaid/blob/4e338a563bac0eaef3caa7374c93120256871665/app/src/main/java/io/plaidapp/data/api/DenvelopingConverter.java
 */

public class PayloadConverter extends Converter.Factory {
    final Gson gson;

    public PayloadConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {

        // This converter requires an annotation providing the name of the payload in the envelope;
        // if one is not supplied then return null to continue down the converter chain.
        final String payloadName = getPayloadName(annotations);
        if (payloadName == null) return null;

        final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                try {
                    JsonReader jsonReader = gson.newJsonReader(body.charStream());
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (payloadName.equals(jsonReader.nextName())) {
                            return adapter.read(jsonReader);
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.close();
                    return null;
                } finally {
                    body.close();
                }
            }
        };
    }


    private String getPayloadName(Annotation[] annotations) {
        if (annotations == null) return null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof PayloadName) {
                return ((PayloadName) annotation).value();
            }
        }
        return null;
    }
}
