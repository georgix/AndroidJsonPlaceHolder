package nz.jing.jsonplaceholder.data.repository

import kotlinx.coroutines.flow.Flow
import nz.jing.jsonplaceholder.data.entity.Comment
import nz.jing.jsonplaceholder.model.Resource

interface ICommentRepository {
    fun getAll(): Flow<Resource<List<Comment>>>
    fun getById(postId: Int): Flow<Resource<List<Comment>>>
}