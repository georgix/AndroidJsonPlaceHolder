package nz.jing.jsonplaceholder.data

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import nz.jing.jsonplaceholder.data.remote.PostApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class PostsApiTest {
    private lateinit var service: PostApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            ))
            .build()
            .create(PostApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPostsTest() = runBlocking {
        enqueueResponse("api-response/posts.json", 200)
        val posts = service.getPosts().body()

        assertThat(posts).isNotNull()
        assertThat(posts!!.size).isEqualTo(100)
        assertThat(posts[1].title).isEqualTo("qui est esse")
    }

    private fun enqueueResponse(localFile: String, code: Int) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(localFile)
        val source = inputStream?.source()?.buffer()
        source?.let {
            mockWebServer.enqueue(MockResponse().setBody(it.readString(Charsets.UTF_8)).setResponseCode(code))
        }
    }
}