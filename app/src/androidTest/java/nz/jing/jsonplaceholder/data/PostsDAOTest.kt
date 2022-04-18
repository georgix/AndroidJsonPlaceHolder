package nz.jing.jsonplaceholder.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.data.local.AppDatabase
import nz.jing.jsonplaceholder.data.local.PostDAO
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostsDAOTest {
    private lateinit var db: AppDatabase
    private lateinit var postsDao: PostDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        postsDao = db.postDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun getPostsTest() = runBlocking {
        val posts = listOf(
            Post(1, 1, "Title 1", "Test body 1")
        )
        postsDao.insertAll(posts)

        val entities = postsDao.getAll().first()
        assertThat(entities).isEqualTo(posts)
    }

    @Test
    fun getPostByIdTest() = runBlocking {
        val posts = listOf(
            Post(1, 1, "Title 1", "Test body 1"),
            Post(2, 3, "Title 3", "Test body 3")
        )
        postsDao.insertAll(posts)

        val post1 = postsDao.getById(1).first()
        assertThat(post1).isEqualTo(posts[0])

        val post3 = postsDao.getById(3).first()
        assertThat(post3).isEqualTo(posts[1])
    }
}