package nz.jing.jsonplaceholder.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.jing.jsonplaceholder.data.entity.Post

@Dao
interface PostDAO {
    @Query("SELECT * FROM post")
    fun getAll(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE ID = :postId")
    fun getById(postId: Int): Flow<Post>

    @Insert
    suspend fun insertAll(posts: List<Post>)

    @Delete
    suspend fun delete(post: Post)
}