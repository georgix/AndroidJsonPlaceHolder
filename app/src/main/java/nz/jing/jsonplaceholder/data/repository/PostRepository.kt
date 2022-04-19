package nz.jing.jsonplaceholder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.data.local.PostDAO
import nz.jing.jsonplaceholder.data.remote.PostApiService
import nz.jing.jsonplaceholder.model.Resource
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val dao: PostDAO,
    private val api: PostApiService
): IPostRepository{
    override fun getAll(): Flow<Resource<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {
            override fun localDataSource(): Flow<List<Post>> =dao.getAll()

            override suspend fun remoteDataSource(): Response<List<Post>> = api.getPosts()

            override suspend fun persistData(data: List<Post>) = dao.insertAll(data)
        }.asFlow()
    }

    override fun getById(postId: Int): Flow<Post> = dao.getById(postId).distinctUntilChanged()

    override fun getSearch(text: String): Flow<Resource<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {
            override fun localDataSource(): Flow<List<Post>> = dao.search(text)

            override suspend fun remoteDataSource(): Response<List<Post>> = api.getPosts()

            override suspend fun persistData(data: List<Post>) = dao.insertAll(data)
        }.asFlow()
    }
}