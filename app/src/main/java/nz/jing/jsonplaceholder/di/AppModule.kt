package nz.jing.jsonplaceholder.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.jing.jsonplaceholder.data.local.AppDatabase
import nz.jing.jsonplaceholder.data.local.PostDAO
import nz.jing.jsonplaceholder.data.remote.ApiServer
import nz.jing.jsonplaceholder.data.remote.PostApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(moshi: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl(ApiServer.BASE_URL)
            .addConverterFactory(moshi)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideRemotePostDataSource(retrofit: Retrofit): PostApiService = retrofit.create(PostApiService::class.java)

    @Singleton
    @Provides
    fun provideLocalPostDataSource(database: AppDatabase): PostDAO = database.postDao()
}