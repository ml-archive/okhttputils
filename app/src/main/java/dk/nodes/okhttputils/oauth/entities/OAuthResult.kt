package dk.nodes.okhttputils.oauth.entities

sealed class OAuthResult<out T> {
    data class Success<T>(val value: T) : OAuthResult<T>()
    data class Error(val exception: Exception) : OAuthResult<Nothing>()
}