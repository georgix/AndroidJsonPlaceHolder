package nz.jing.jsonplaceholder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.data.repository.IPostRepository
import nz.jing.jsonplaceholder.model.Result
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val postRepository: IPostRepository): ViewModel() {
    private val _posts: MutableStateFlow<Result<List<Post>>> = MutableStateFlow(Result.Loading)
    val posts: StateFlow<Result<List<Post>>> = _posts


    fun getPosts() {
        viewModelScope.launch {
            postRepository.getAll()
                .map {Result.fromResource(it)}
                .collect { _posts.value = it }
        }
    }

    fun getSearchPosts(text: String) {
        viewModelScope.launch {
            postRepository.getSearch(text)
                .map { Result.fromResource(it) }
                .collect { _posts.value = it }
        }
    }

    fun search(text: String) {
        getSearchPosts(text)
    }
}