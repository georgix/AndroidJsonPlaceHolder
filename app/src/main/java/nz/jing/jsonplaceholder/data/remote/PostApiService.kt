package nz.jing.jsonplaceholder.data.remote

import nz.jing.jsonplaceholder.data.entity.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {
    @GET(ApiServer.POSTS_URL)
    suspend fun getPosts(): Response<List<Post>>

    @GET("${ApiServer.POSTS_URL}/{:id}")
    suspend fun  getPostById(id: Int): Response<Post>
}