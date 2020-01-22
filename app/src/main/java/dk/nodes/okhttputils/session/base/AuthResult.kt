package dk.nodes.okhttputils.session.base

sealed class AuthResult<out T : AuthInfo> {

    data class Success<out T: AuthInfo>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "AuthSuccess[data=$data]"
            is Error -> "AuthError[exception=$exception]"
        }
    }
}