package nz.jing.jsonplaceholder.model

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val msg: String): Result<T>()
    object Loading: Result<Nothing>()

    val success get() = this is Success && data != null
    val error get() = this is Error
    val loading get() = this is Loading

    companion object {
        fun <T> fromResource(resource: Resource<T>): Result<T> = when (resource) {
            is Resource.Success -> Success(resource.data)
            is Resource.Error -> Error(resource.msg)
        }
    }
}
