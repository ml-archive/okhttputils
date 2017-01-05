package dk.nodes.okhttputils.error;

/**
 * Created by bison on 09/12/16.
 */

public class HttpErrorHandler {
    public String errorMessage = null;
    public boolean finishApp;

    public HttpErrorHandler(String errorMessage) {
        this.errorMessage = errorMessage;
        this.finishApp = false;
    }

    public HttpErrorHandler(String errorMessage, boolean finishApp) {
        this.errorMessage = errorMessage;
        this.finishApp = finishApp;
    }

    /**
     * This is the error handler function you need to override. Return true if you wish to show a
     * dialog with the errorMessage. If errorMessage is null a default hardcoded error message will
     * be shown
     * @param errorCode
     * @param url
     * @return
     */
    public boolean onError(int errorCode, String url)
    {
        return true;
    }

    public void onDialogClosed() {}
}
