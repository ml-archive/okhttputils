package dk.nodes.okhttputils

import dk.nodes.okhttputils.oauth.OAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApiFactory(authInterceptor: OAuthInterceptor) {

    private val okHttpClient: OkHttpClient
    private val retrofitBuilder: Retrofit.Builder

    init {
        val level = HttpLoggingInterceptor.Level.BODY
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(level)
        val gsonConverterFactory = GsonConverterFactory.create()

        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

        retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
    }

    fun <T> createApi(apiClass: Class<T>, baseUrl: String): T {
        return retrofitBuilder.baseUrl(baseUrl).build().create(apiClass)
    }
}