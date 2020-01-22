package dk.nodes.okhttputils.session.base


data class AuthInfo(
        val accessToken: String,
        val refreshToken: String
)