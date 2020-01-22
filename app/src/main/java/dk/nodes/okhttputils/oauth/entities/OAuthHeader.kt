package dk.nodes.okhttputils.oauth.entities

data class OAuthHeader(
        val headerName: String = "Authorization",
        val headerValuePrefix: String = "Bearer "
) {
    fun headerValue(value: String): String = "${headerValuePrefix}$value"
}