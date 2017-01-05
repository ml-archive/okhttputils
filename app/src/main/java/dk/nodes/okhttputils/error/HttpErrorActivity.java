package dk.nodes.okhttputils.error;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import dk.nodes.okhttputils.R;

/**
 * Created by bison on 01/11/16.
 */

public class HttpErrorActivity extends Activity {
    public static final String TAG = HttpErrorActivity.class.getSimpleName();
    private int errorCode;
    private String errorMsg;
    public boolean finishApp = false;
    public static HttpErrorHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        errorCode = i.getIntExtra("errorCode", -1);
        errorMsg = i.getStringExtra("errorMsg");
        finishApp = i.getBooleanExtra("finishApp", false);
        if(errorMsg == null)
        {
            errorMsg = "Unknown error occured. (Code " + errorCode + ")";
        }

        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setMessage(errorMsg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(finishApp)
                            finishAffinity();
                        else
                            finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if(finishApp)
                            finishAffinity();
                        else
                            finish();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null)
            handler.onDialogClosed();
    }

}
