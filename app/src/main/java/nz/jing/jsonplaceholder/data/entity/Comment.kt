package nz.jing.jsonplaceholder.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @ColumnInfo(name = "post_id") val postId: Int,
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val body: String
)