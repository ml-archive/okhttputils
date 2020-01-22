package dk.nodes.okhttputils.session.base

interface OAuthCallback {

    /**
     * Called if new [OAuthInfo] is required.
     *
     * @param refreshToken the current Refresh Token or null if it was not set in [OAuthRepository]. Remember to call [OAuthRepository.setRefreshToken] as soon as you have a signed in user. Also keep in mind that your Refresh Token can always expire, in which case you probably have to sign-out your user.
     *
     * @return [OAuthResult] with newly generated Access Token
     */
    fun provideAuthInfo(refreshToken: String?): OAuthResult<OAuthInfo>
}