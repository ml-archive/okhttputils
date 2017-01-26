package dk.nodes.okhttputils.error;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by bison right about now (its not the funk soul brothers)
 */
public class ErrorInterceptor implements Interceptor
{
    public static final String TAG = ErrorInterceptor.class.getSimpleName();
    public int errorCode = -1;
    public String errorMsg = "Unknown error";
    public boolean finishApp = false;


    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpErrorActivity.handler = null;
        try {
            Response response = chain.proceed(chain.request());

            if(!response.isSuccessful()) {
                errorCode = response.code();

                HttpErrorHandler handler = HttpErrorManager.getHandler(errorCode);
                if(handler != null)
                {
                    HttpErrorActivity.handler = handler;
                    errorMsg = handler.errorMessage;
                    finishApp = handler.finishApp;
                    boolean show_dialog = handler.onError(errorCode, response.request().url().toString());
                    if(show_dialog)
                        launchErrorActivity();
                }
            }
            return response;
        }
        catch(Exception e)
        {
            if(e instanceof java.net.UnknownHostException)
            {
                errorMsg = HttpErrorManager.NO_INTERNET;
            }
            launchErrorActivity();
            e.printStackTrace();
            throw(e);
        }
    }

    public void launchErrorActivity()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Context context = HttpErrorManager.context;
                Intent i = new Intent(context, HttpErrorActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.putExtra("errorCode", errorCode);
                i.putExtra("errorMsg", errorMsg);
                i.putExtra("finishApp", finishApp);
                context.startActivity(i);
            }
        });
    }
}
