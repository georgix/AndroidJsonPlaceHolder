package nz.jing.jsonplaceholder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nz.jing.jsonplaceholder.data.entity.Comment
import nz.jing.jsonplaceholder.data.repository.ICommentRepository
import nz.jing.jsonplaceholder.model.Result
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(private val commentRepository: ICommentRepository): ViewModel() {
    private val _comments: MutableStateFlow<Result<List<Comment>>> = MutableStateFlow(Result.Loading)
    val comments: StateFlow<Result<List<Comment>>> = _comments

    fun getAllComments() {
        viewModelScope.launch {
            commentRepository.getAll()
                .map {Result.fromResource(it)}
                .collect { _comments.value = it }
        }
    }

    fun getCommentsForPost(postId: Int) {
        viewModelScope.launch {
            commentRepository.getById(postId)
                .map { Result.fromResource(it) }
                .collect { _comments.value = it }
        }
    }
}