package dk.nodes.okhttputils.session.interceptors

import dk.nodes.okhttputils.session.HeaderInfo
import dk.nodes.okhttputils.session.base.AccessTokenRefresher
import dk.nodes.okhttputils.session.base.AccessTokenRepository
import dk.nodes.okhttputils.session.base.AuthResult
import okhttp3.*

class AccessTokenAuthenticator internal constructor(
        private val tokenRepository: AccessTokenRepository,
        private val refresher: AccessTokenRefresher,
        private val headerInfo: HeaderInfo) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code() == 401) {
            proceedWithTokenRefreshment(response)
        } else {
            response.request()
        }
    }

    private fun proceedWithTokenRefreshment(response: Response?): Request? {

        // Ask refresher for a new token
        val refreshToken = tokenRepository.getRefreshToken() ?: return null
        val result = refresher.retrieveNewToken(refreshToken)
        return when (result) {
            is AuthResult.Error -> {
                 null
            }
            // Persist new Token value and proceed with the request
            is AuthResult.Success -> {
                tokenRepository.persistToken(result.data.getAccessToken())
                tokenRepository.persistRefreshToken(result.data.getRefreshToken())
                return response
                        ?.request()
                        ?.newBuilder()
                        ?.header(headerInfo.authHeaderName, "${headerInfo.authHeaderPrefix} $result".trim())
                        ?.build()
            }
        }

    }

}