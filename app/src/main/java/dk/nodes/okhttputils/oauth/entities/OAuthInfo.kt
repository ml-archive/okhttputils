package dk.nodes.okhttputils.oauth.entities

data class OAuthInfo(
        val accessToken: String,
        val refreshToken: String
)