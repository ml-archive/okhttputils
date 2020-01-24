package dk.nodes.okhttputils.extensions.okhttpclient

import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addConditionalInterceptor(
        condition: Boolean,
        interceptor: Interceptor
): OkHttpClient.Builder = if (condition) {
    addInterceptor(interceptor)
} else {
    this
}