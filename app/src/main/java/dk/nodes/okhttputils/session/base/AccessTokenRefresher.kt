package dk.nodes.okhttputils.session.base

interface AccessTokenRefresher {


    /**
     * Attempts to refresh an accessToken
     * @return AuthResult with newly generated access token in case of success
     */
    fun retrieveNewToken(refreshToken: String) : AuthResult<AccessToken>
}