package dk.nodes.okhttputils.oauth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import dk.nodes.okhttputils.TestApi
import dk.nodes.okhttputils.TestApiFactory
import dk.nodes.okhttputils.TestData
import dk.nodes.okhttputils.TestRepository
import dk.nodes.okhttputils.oauth.entities.OAuthHeader
import dk.nodes.okhttputils.oauth.entities.OAuthInfo
import dk.nodes.okhttputils.oauth.entities.OAuthResult
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class OAuthAuthenticatorTest {

    private val baseUrl = "/"
    private val testData = TestData("test_value")
    private val testDataJson = Gson().toJson(testData)

    private val mockWebServer = MockWebServer()
    private val httpUrl = mockWebServer.url(baseUrl)

    private val oAuthHeader = OAuthHeader()
    private val oAuthRepository = TestRepository()

    private val oAuthCallback = mockk<OAuthCallback> {
        every { provideAuthInfo("test_refresh_token") } returns OAuthResult.Success(OAuthInfo(
                accessToken = "test_access_token_from_callback",
                refreshToken = "test_refresh_token_from_callback"))
    }

    private lateinit var testApi: TestApi

    @Before
    fun setUp() {
        val authenticator = OAuthAuthenticator(oAuthRepository, oAuthCallback, oAuthHeader)
        val apiFactory = TestApiFactory(authenticator = authenticator)
        testApi = apiFactory.createApi(TestApi::class.java, httpUrl.toString())
    }

    @Test
    fun `When call fails with 401, Then authenticator refreshes tokens`() {
        oAuthRepository.setRefreshToken("test_access_token")
        oAuthRepository.setRefreshToken("test_refresh_token")

        val invalidTokenResponse = MockResponse().setResponseCode(401)
        val refreshResponse = MockResponse().setResponseCode(200).setBody(testDataJson)

        // Enqueue 401 response
        mockWebServer.enqueue(invalidTokenResponse)
        // Enqueue 200 original response
        mockWebServer.enqueue(refreshResponse)

        testApi.test().execute()

        mockWebServer.takeRequest()
        val request = mockWebServer.takeRequest()
        val header = request.getHeader(oAuthHeader.name)

        // Verify new tokens
        assertThat(oAuthRepository.getAccessToken()).isEqualTo("test_access_token_from_callback")
        assertThat(oAuthRepository.getRefreshToken()).isEqualTo("test_refresh_token_from_callback")

        // Verify new access-token header
        assertThat(header).isEqualTo(oAuthHeader.value("test_access_token_from_callback"))
    }
}