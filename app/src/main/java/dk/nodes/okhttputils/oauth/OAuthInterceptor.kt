package dk.nodes.okhttputils.oauth

import dk.nodes.okhttputils.oauth.entities.OAuthHeaderInfo
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(private val repository: OAuthRepository,
                       private val headerInfo: OAuthHeaderInfo) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = repository.getAccessToken()
        return if (token.isNullOrBlank()) {
            chain.proceed(request)
        } else {
            val newRequest = request.newBuilder()
                    .header(headerInfo.headerName, "${headerInfo.headerPrefix}$token".trim())
                    .build()
            chain.proceed(newRequest)
        }
    }
}