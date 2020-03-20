package dk.nodes.okhttputils.oauth

import dk.nodes.okhttputils.oauth.entities.OAuthHeader
import dk.nodes.okhttputils.oauth.entities.OAuthResult
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuthAuthenticator(
        private val repository: OAuthRepository,
        private val callback: OAuthCallback,
        private val header: OAuthHeader
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == 401) {
            response.request.applyOAuthInfo()
        } else {
            response.request
        }
    }

    private fun Request.applyOAuthInfo(): Request? {
        val refreshToken = repository.getRefreshToken()

        return when (val result = callback.provideAuthInfo(refreshToken)) {
            is OAuthResult.Error -> {
                null
            }
            is OAuthResult.Success -> {
                repository.setAccessToken(result.value.accessToken)
                repository.setRefreshToken(result.value.refreshToken)

                newBuilder()
                        .header(header.name, header.value(result.value.accessToken))
                        .build()
            }
        }
    }
}
