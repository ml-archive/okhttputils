package dk.nodes.okhttputils.error;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bison on 09/12/16.
 */

public class HttpErrorManager {
    static HashMap<Integer, HttpErrorHandler> errorHandlerMap = new HashMap<>();
    public static String NO_INTERNET = "No internet connection";
    public static String TIMEOUT = "Timeout";
    public static Context context;


    public static void setHandler(int code, HttpErrorHandler handler)
    {
        errorHandlerMap.put(code, handler);
    }

    public static void removeHandler(int code)
    {
        if(errorHandlerMap.containsKey(code))
            errorHandlerMap.remove(code);
    }

    public static void removeHandler(HttpErrorHandler handler)
    {
        if(!errorHandlerMap.containsValue(handler))
            return;
        for(Map.Entry<Integer, HttpErrorHandler> entry : errorHandlerMap.entrySet())
        {
            if(entry.getValue() == handler)
            {
                errorHandlerMap.remove(entry.getKey());
                return;
            }
        }
    }

    public static void runHandler(int code, String url)
    {
        if(!errorHandlerMap.containsKey(code))
            return;
        HttpErrorHandler handler = errorHandlerMap.get(code);
        handler.onError(code, url);
    }

    public static HttpErrorHandler getHandler(int code)
    {
        if(!errorHandlerMap.containsKey(code))
            return null;
        HttpErrorHandler handler = errorHandlerMap.get(code);
        return handler;
    }
}
