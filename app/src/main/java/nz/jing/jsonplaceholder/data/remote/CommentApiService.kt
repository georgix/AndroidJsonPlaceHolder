package nz.jing.jsonplaceholder.data.remote

import nz.jing.jsonplaceholder.data.entity.Comment
import retrofit2.Response
import retrofit2.http.GET

interface CommentApiService {
    @GET(ApiServer.COMMENTS_URL)
    suspend fun getComments(): Response<List<Comment>>

    @GET("${ApiServer.COMMENTS_URL}?postId={:id}")
    suspend fun  getCommentById(postId: Int): Response<List<Comment>>

}