package dk.nodes.okhttputils.oauth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import dk.nodes.okhttputils.TestApi
import dk.nodes.okhttputils.TestApiFactory
import dk.nodes.okhttputils.TestData
import dk.nodes.okhttputils.TestRepository
import dk.nodes.okhttputils.oauth.entities.OAuthHeader
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class OAuthInterceptorTest {

    private val baseUrl = "/"
    private val testData = TestData("test_value")
    private val testDataJson = Gson().toJson(testData)

    private val mockWebServer = MockWebServer()
    private val httpUrl = mockWebServer.url(baseUrl)

    private val oAuthHeader = OAuthHeader()
    private val oAuthRepository = TestRepository()

    private lateinit var testApi: TestApi

    @Before
    fun setUp() {
        val oAuthInterceptor = OAuthInterceptor(oAuthRepository, oAuthHeader)
        val apiFactory = TestApiFactory(interceptor = oAuthInterceptor)
        testApi = apiFactory.createApi(TestApi::class.java, httpUrl.toString())
    }

    @Test
    fun `When a call is done, Then OAuthHeader is added`() {
        val accessToken = "test_access_token"
        val successResponse = MockResponse().setBody(testDataJson)

        oAuthRepository.setAccessToken(accessToken)
        mockWebServer.enqueue(successResponse)

        testApi.test().execute()

        val request = mockWebServer.takeRequest()
        val header = request.getHeader(oAuthHeader.name)

        assertThat(header).isEqualTo(oAuthHeader.value(accessToken))
    }
}