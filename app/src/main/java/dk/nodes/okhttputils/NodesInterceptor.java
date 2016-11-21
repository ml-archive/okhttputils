package dk.nodes.okhttputils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by joso on 13/05/16.
 */
public class NodesInterceptor implements Interceptor {

    private static String metaHeader = "";
    public static String metaHeaderKey = "N-Meta";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .header(metaHeaderKey, metaHeader)
                .header("Accept", "application/vnd.nodes.v1+json")
                .build();

        return chain.proceed(request);
    }

    public static void makeMetaHeader(String environment, String appVersion, String osVersion, String device) {
        metaHeader = String.format(
                "android;%s;%s;%s;%s",
                environment,
                appVersion,
                osVersion,
                device
        );
    }
}
