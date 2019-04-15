package dk.nodes.okhttputils.session.base

/**
 * Access Token Abstraction
 */
interface AccessToken {

    /**
     * Returns token value that is used to access the resource
     */
    fun getAccessToken() : String

    /**
     * Return value of the token used to generate new access token
     */
    fun getRefreshToken() : String?
}