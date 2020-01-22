package dk.nodes.okhttputils.oauth.entities

sealed class OAuthResult<out T> {

    data class Success<out T>(val data: T) : OAuthResult<T>()
    data class Error(val exception: Exception) : OAuthResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "AuthSuccess[data=$data]"
            is Error -> "AuthError[exception=$exception]"
        }
    }
}