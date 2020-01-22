package dk.nodes.okhttputils.session

data class OAuthHeaderInfo(
        val headerName: String = "Authorization",
        val headerPrefix: String = "Bearer "
)