package nz.jing.jsonplaceholder.data.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import nz.jing.jsonplaceholder.model.Resource
import retrofit2.Response

abstract class NetworkBoundRepository<TResult, TRequest> {
    private val TAG = NetworkBoundRepository::class.java.simpleName

    fun asFlow() = flow<Resource<TResult>> {
        emit(Resource.Success(localDataSource().first()))

        val response = remoteDataSource()
        if (response.isSuccessful) {
            response.body()?.let { persistData(it) }
        } else {
            emit(Resource.Error(response.message()))
        }
        emitAll(localDataSource().map {
            Resource.Success(it)
        })
    }.catch { e ->
        emit(Resource.Error("Please check the Internet connection."))
        Log.e(TAG, "asFlow exception", e)
    }

    @MainThread
    protected abstract fun localDataSource(): Flow<TResult>

    @MainThread
    protected abstract suspend fun remoteDataSource(): Response<TRequest>

    @WorkerThread
    protected abstract suspend fun persistData(data: TRequest)
}