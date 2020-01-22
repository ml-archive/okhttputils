package dk.nodes.okhttputils.session.base

sealed class AuthResult<out T> {

    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "AuthSuccess[data=$data]"
            is Error -> "AuthError[exception=$exception]"
        }
    }
}