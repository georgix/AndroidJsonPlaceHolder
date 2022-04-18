package nz.jing.jsonplaceholder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import nz.jing.jsonplaceholder.data.repository.IPostRepository
import nz.jing.jsonplaceholder.data.repository.PostRepository

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ActivityModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindPostRepository(repository: PostRepository): IPostRepository
}