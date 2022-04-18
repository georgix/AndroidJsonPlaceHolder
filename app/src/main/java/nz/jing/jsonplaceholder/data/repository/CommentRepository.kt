package nz.jing.jsonplaceholder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import nz.jing.jsonplaceholder.data.entity.Comment
import nz.jing.jsonplaceholder.data.local.CommentDAO
import nz.jing.jsonplaceholder.data.remote.CommentApiService
import nz.jing.jsonplaceholder.model.Resource
import retrofit2.Response
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val dao: CommentDAO,
    private val api: CommentApiService
): ICommentRepository{
    override fun getAll(): Flow<Resource<List<Comment>>> {
        return object : NetworkBoundRepository<List<Comment>, List<Comment>>() {
            override fun localDataSource(): Flow<List<Comment>> =dao.getAll()

            override suspend fun remoteDataSource(): Response<List<Comment>> = api.getComments()

            override suspend fun persistData(data: List<Comment>) = dao.insertAll(data)
        }.asFlow()
    }

    override fun getById(postId: Int): Flow<Resource<List<Comment>>> =
        object : NetworkBoundRepository<List<Comment>, List<Comment>>() {
            override fun localDataSource(): Flow<List<Comment>> = dao.getByPostId(postId)

            override suspend fun remoteDataSource(): Response<List<Comment>> = api.getCommentById(postId)

            override suspend fun persistData(data: List<Comment>) = dao.insertAll(data)
        }.asFlow()
}