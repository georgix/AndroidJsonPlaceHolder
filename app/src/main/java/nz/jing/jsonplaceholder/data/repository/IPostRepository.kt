package nz.jing.jsonplaceholder.data.repository

import kotlinx.coroutines.flow.Flow
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.model.Resource

interface IPostRepository {
    fun getAll(): Flow<Resource<List<Post>>>
    fun getById(postId: Int): Flow<Post>
}