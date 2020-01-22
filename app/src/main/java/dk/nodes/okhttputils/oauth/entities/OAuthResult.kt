package dk.nodes.okhttputils.oauth.entities

sealed class OAuthResult<out T> {
    data class Success<out T>(val value: T) : OAuthResult<T>()
    data class Error(val exception: Exception) : OAuthResult<Nothing>()
}