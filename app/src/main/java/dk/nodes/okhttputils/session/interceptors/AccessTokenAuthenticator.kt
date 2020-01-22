package dk.nodes.okhttputils.session.interceptors

import dk.nodes.okhttputils.session.AuthHeaderInfo
import dk.nodes.okhttputils.session.base.AccessTokenRefresher
import dk.nodes.okhttputils.session.base.AuthRepository
import dk.nodes.okhttputils.session.base.AuthResult
import okhttp3.*

class AccessTokenAuthenticator internal constructor(
        private val tokenRepository: AuthRepository,
        private val refresher: AccessTokenRefresher,
        private val authHeaderInfo: AuthHeaderInfo) : Authenticator {


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
                tokenRepository.setAccessToken(result.data.getAccessToken())
                tokenRepository.setRefreshToken(result.data.getRefreshToken())
                return response
                        ?.request()
                        ?.newBuilder()
                        ?.header(authHeaderInfo.headerName, "${authHeaderInfo.headerPrefix}$result".trim())
                        ?.build()
            }
        }

    }

}