package nz.jing.jsonplaceholder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import nz.jing.jsonplaceholder.data.entity.Comment
import nz.jing.jsonplaceholder.data.entity.Post

@Database(
    entities = [Post::class, Comment::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDAO

    abstract fun commentDao(): CommentDAO

    companion object {
        const val DB_NAME = "jsonplaceholder_database"
    }
}