package dk.nodes.okhttputils

import dk.nodes.okhttputils.oauth.OAuthRepository

class TestRepository : OAuthRepository {

    private var accessToken: String? = null
    private var refreshToken: String? = null

    override fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    override fun getAccessToken(): String? {
        return this.accessToken
    }

    override fun setRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
    }

    override fun getRefreshToken(): String? {
        return refreshToken
    }

    override fun clear() {
        accessToken = null
        refreshToken = null
    }
}