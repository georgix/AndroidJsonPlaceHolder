package nz.jing.jsonplaceholder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.jing.jsonplaceholder.data.remote.ApiServer
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
        return MoshiConverterFactory.create()
    }
}