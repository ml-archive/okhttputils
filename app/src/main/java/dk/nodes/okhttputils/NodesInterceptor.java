package dk.nodes.okhttputils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by joso on 13/05/16.
 */
public class NodesInterceptor implements Interceptor {

    private String metaHeader = "";
    private String metaHeaderKey = "N-Meta";
    private String environment = "";
    private String appVersion = "";
    private String osVersion = "";
    private String device = "";

    public NodesInterceptor(String metaKey, String environment, String appVersion, String osVersion, String device) {
        this.metaHeaderKey = metaKey;
        this.environment = environment;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.device = device;
    }

    public NodesInterceptor(String environment, String appVersion, String osVersion, String device) {
        this.environment = environment;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.device = device;
    }

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

    public void makeMetaHeader() {
        metaHeader = String.format(
                "android;%s;%s;%s;%s",
                environment,
                appVersion,
                osVersion,
                device
        );
    }
}
