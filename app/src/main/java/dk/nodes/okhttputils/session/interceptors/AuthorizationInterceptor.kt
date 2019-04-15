package dk.nodes.okhttputils.session.interceptors

import dk.nodes.okhttputils.session.HeaderInfo
import dk.nodes.okhttputils.session.base.AccessTokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val tokenRepository: AccessTokenRepository,
                               private val headerInfo: HeaderInfo) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = tokenRepository.retrieveToken()
        return if (token.isNullOrBlank()) {
            chain.proceed(request)
        } else {
            val newRequest = request.newBuilder()
                    .header(headerInfo.authHeaderName, "${headerInfo.authHeaderPrefix} $token".trim())
                    .build()
            chain.proceed(newRequest)
        }
    }
}