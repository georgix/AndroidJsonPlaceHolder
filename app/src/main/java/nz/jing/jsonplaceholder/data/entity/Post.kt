package nz.jing.jsonplaceholder.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post (
    @ColumnInfo(name = "user_id") val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val body: String)