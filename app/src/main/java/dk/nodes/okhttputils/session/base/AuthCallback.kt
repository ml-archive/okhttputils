package dk.nodes.okhttputils.session.base

interface AuthCallback {

    /**
     * Called if new [AuthInfo] is required.
     *
     * @param refreshToken the current refreshToken or null if it was not set in [AuthRepository]. Remember to call [AuthRepository.setRefreshToken] as soon as you have a signed in user. Also keep in mind that your [refreshToken] can always expire, in which case you probably have to sign-out your user.
     *
     * @return [AuthResult] with newly generated AccessToken
     */
    fun provideAuthInfo(refreshToken: String?): AuthResult<AuthInfo>
}