# okhttputils
Contains various different utilities for the OkHttp library usable across multiple projects.

## HttpErrorManager
This is a global error handler for http requests performed by the OkHttp library.
The idea is that instead of having to handle common error codes like 442 (invalid token) each place
you make the request, you implement a handler for the error code once and for all.

There is no initialisation since the library use the firebase content provider trick to obtain a Context.

### Usage
To show a message in a dialog when a certain error code is returned:

```java
HttpErrorManager.setHandler(500, new HttpErrorHandler("Ach nein! eine internal server fehler has passiert!!!", true);
```

To show a message in a dialog and run some code after the user dismiss it:
```java
HttpErrorHandler invalidAuthHandler = new HttpErrorHandler("Ach nein! deine Token sind KAPUT!!") {
        @Override
        public boolean onError(int code, String url) {
            // Only show dialog if the endpoint url does not contain the word login
            if(url.contains("login")) {
                return false;
            }
            return true;
        }

        @Override
        public void onDialogClosed() {
            // This code gets run after the user dismissed the dialog         
            Intent intent = new Intent(App.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
};

// register the handler above for error code 442 and 443
HttpErrorManager.setHandler(442, invalidAuthHandler);
HttpErrorManager.setHandler(443, invalidAuthHandler);
```

If the user returns true from the onError method the dialog is shown. If no dialog is shown the onDialogClosed function
will not get called.


## Installation
```groovy
compile ('dk.nodes.utils:okhttp:0.7')
{
    exclude module: 'retrofit:2.1.0'
    exclude module: 'converter-gson:2.1.0'
    exclude module: 'okhttp:3.4.1'
    exclude module: 'gson:2.8.0'
}
```

Remove the excludes if the libraries are not already present in your app (they most likely are :D)
