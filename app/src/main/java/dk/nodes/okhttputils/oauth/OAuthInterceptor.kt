package dk.nodes.okhttputils.oauth

import dk.nodes.okhttputils.oauth.entities.OAuthHeader
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(private val repository: OAuthRepository,
                       private val header: OAuthHeader) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = repository.getAccessToken()
        return if (accessToken.isNullOrBlank()) {
            chain.proceed(request)
        } else {
            val newRequest = request.newBuilder()
                    .header(header.headerName, header.headerValue(accessToken))
                    .build()
            chain.proceed(newRequest)
        }
    }
}