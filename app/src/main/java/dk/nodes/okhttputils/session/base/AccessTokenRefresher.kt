package dk.nodes.okhttputils.session.base

interface AccessTokenRefresher {


    /**
     * Attempts to refresh an accessToken
     *
     * @param refreshToken the current refreshToken or null if it was not set in [AuthRepository]. Call [AuthRepository.setRefreshToken] as soon as you have a signed in user. Keep in mind that your [refreshToken] can always expire.
     *
     * @return AuthResult with newly generated access token in case of success
     */
    fun retrieveNewToken(refreshToken: String?): AuthResult<AccessToken>
}