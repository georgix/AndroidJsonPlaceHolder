package nz.jing.jsonplaceholder.data.remote

interface ApiServer {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val POSTS_URL = "/posts"
        const val COMMENTS_URL = "/comments"
    }
}