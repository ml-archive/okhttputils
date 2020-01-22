package dk.nodes.okhttputils.oauth

/**
 * A local repository for storing tokens. Implementation normally would use SharedPreferences or Room.
 */
interface OAuthRepository {

    /**
     * Stores the Access Token
     */
    fun setAccessToken(accessToken: String?)

    /**
     * @return the stored Access Token
     */
    fun getAccessToken(): String?

    /**
     * Stores the Refresh Token
     */
    fun setRefreshToken(refreshToken: String?)

    /**
     * @return the stored Refresh Token
     */
    fun getRefreshToken(): String?


    /**
     * Clears all stored tokens. Call this if a user signs out.
     */
    fun clear()
}