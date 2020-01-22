package dk.nodes.okhttputils.session

data class AuthHeaderInfo(
        val headerName: String = "Authorization",
        val headerPrefix: String = "Bearer "
)