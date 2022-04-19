package nz.jing.jsonplaceholder.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.jing.jsonplaceholder.data.entity.Post

@Dao
interface PostDAO {
    @Query("SELECT * FROM post")
    fun getAll(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE ID = :postId")
    fun getById(postId: Int): Flow<Post>

    @Query("SELECT * FROM post WHERE title LIKE '%' || :text || '%'")
    fun search(text: String): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Delete
    suspend fun delete(post: Post)
}