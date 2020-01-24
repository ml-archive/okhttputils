package dk.nodes.okhttputils

import retrofit2.Call
import retrofit2.http.GET

interface TestApi {

    @GET("/test")
    fun test(): Call<TestData>
}