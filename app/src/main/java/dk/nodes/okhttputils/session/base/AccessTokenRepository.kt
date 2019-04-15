package dk.nodes.okhttputils.session.base


interface AccessTokenRepository {

    /**
     * Saves value of the Access Token
     */
    fun persistToken(token: String)

    /**
     * @return value of the stored Access Token
     */
    fun retrieveToken() : String?

    /**
     * @return value of the access Token
     */
    fun getRefreshToken(): String?


    /**
     * Saves value of the refresh token
     */
    fun persistRefreshToken(token: String?)

}