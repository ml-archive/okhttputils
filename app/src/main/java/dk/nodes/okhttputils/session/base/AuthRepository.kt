package dk.nodes.okhttputils.session.base

/**
 * A local (SharedPreferences, Room, ...) repository for storing tokens.
 */
interface AuthRepository {

    /**
     * Stores the Access Token
     */
    fun setAccessToken(accessToken: String?)

    /**
     * @return the stored Access Token
     */
    fun getAccessToken(): String

    /**
     * Stores the Refresh Token
     */
    fun setRefreshToken(refreshToken: String?)

    /**
     * @return the stored Refresh Token
     */
    fun getRefreshToken(): String?


    /**
     * Clears all stored tokens
     */
    fun clear()
}