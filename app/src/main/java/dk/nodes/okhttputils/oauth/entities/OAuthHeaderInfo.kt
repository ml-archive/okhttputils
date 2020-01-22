package dk.nodes.okhttputils.oauth.entities

data class OAuthHeaderInfo(
        val headerName: String = "Authorization",
        val headerPrefix: String = "Bearer "
)