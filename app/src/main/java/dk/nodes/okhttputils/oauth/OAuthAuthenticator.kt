package dk.nodes.okhttputils.oauth

import dk.nodes.okhttputils.oauth.entities.OAuthHeaderInfo
import dk.nodes.okhttputils.oauth.entities.OAuthResult
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuthAuthenticator internal constructor(
        private val repository: OAuthRepository,
        private val callback: OAuthCallback,
        private val headerInfo: OAuthHeaderInfo) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code() == 401) {
            proceedWithTokenRefreshment(response)
        } else {
            response.request()
        }
    }

    private fun proceedWithTokenRefreshment(response: Response?): Request? {
        // Ask for new AuthInfo
        val refreshToken = repository.getRefreshToken()
        return when (val result = callback.provideAuthInfo(refreshToken)) {
            is OAuthResult.Error -> {
                null
            }
            // Persist new AuthInfo and proceed with the request
            is OAuthResult.Success -> {
                repository.setAccessToken(result.data.accessToken)
                repository.setRefreshToken(result.data.refreshToken)
                return response
                        ?.request()
                        ?.newBuilder()
                        ?.header(headerInfo.headerName, "${headerInfo.headerPrefix}$result".trim())
                        ?.build()
            }
        }

    }

}