package dk.nodes.okhttputils.session.base

data class OAuthInfo(
        val accessToken: String,
        val refreshToken: String
)