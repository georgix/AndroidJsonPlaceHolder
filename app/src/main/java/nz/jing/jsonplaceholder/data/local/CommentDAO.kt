package nz.jing.jsonplaceholder.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.jing.jsonplaceholder.data.entity.Comment

@Dao
interface CommentDAO {
    @Query("SELECT * FROM comment")
    fun getAll(): Flow<List<Comment>>

    @Query("SELECT * FROM comment WHERE post_id = :postId")
    fun getByPostId(postId: Int): Flow<List<Comment>>

    @Query("SELECT * FROM comment WHERE id = :id")
    fun getById(id: Int): Flow<Comment>

    @Insert
    suspend fun insertAll(posts: List<Comment>)

    @Delete
    suspend fun delete(post: Comment)
}