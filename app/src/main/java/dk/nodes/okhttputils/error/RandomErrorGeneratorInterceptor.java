package dk.nodes.okhttputils.error;

import java.io.IOException;
import java.util.Random;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by bison right about now (its not the funk soul brothers)
 */
public class RandomErrorGeneratorInterceptor implements Interceptor
{
    public static final String TAG = RandomErrorGeneratorInterceptor.class.getSimpleName();


    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpErrorActivity.handler = null;
        try {
            Response response = chain.proceed(chain.request());
            // if it failed for real, just post it back, real errors are better than fake errors
            if(!response.isSuccessful()) {
                return response;
            }

            Random r = new Random();
            //int error_code = 400 + r.nextInt(199);
            int error_code = 500;
            Response.Builder builder = response.newBuilder();
            builder.code(error_code);
            response = builder.build();
            return response;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw(e);
        }
    }
}
