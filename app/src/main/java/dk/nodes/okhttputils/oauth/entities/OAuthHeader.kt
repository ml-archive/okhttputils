package dk.nodes.okhttputils.oauth.entities

data class OAuthHeader(
        val name: String = "Authorization",
        val valuePrefix: String = "Bearer "
) {
    fun value(value: String): String = "${valuePrefix}$value"
}