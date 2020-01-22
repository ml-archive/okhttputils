package dk.nodes.okhttputils.session.interceptors

import dk.nodes.okhttputils.session.AuthHeaderInfo
import dk.nodes.okhttputils.session.base.AuthCallback
import dk.nodes.okhttputils.session.base.AuthRepository
import dk.nodes.okhttputils.session.base.AuthResult
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator internal constructor(
        private val tokenRepository: AuthRepository,
        private val callback: AuthCallback,
        private val authHeaderInfo: AuthHeaderInfo) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code() == 401) {
            proceedWithTokenRefreshment(response)
        } else {
            response.request()
        }
    }

    private fun proceedWithTokenRefreshment(response: Response?): Request? {
        // Ask for new AuthInfo
        val refreshToken = tokenRepository.getRefreshToken()
        return when (val result = callback.provideAuthInfo(refreshToken)) {
            is AuthResult.Error -> {
                null
            }
            // Persist new AuthInfo and proceed with the request
            is AuthResult.Success -> {
                tokenRepository.setAccessToken(result.data.accessToken)
                tokenRepository.setRefreshToken(result.data.refreshToken)
                return response
                        ?.request()
                        ?.newBuilder()
                        ?.header(authHeaderInfo.headerName, "${authHeaderInfo.headerPrefix}$result".trim())
                        ?.build()
            }
        }

    }

}