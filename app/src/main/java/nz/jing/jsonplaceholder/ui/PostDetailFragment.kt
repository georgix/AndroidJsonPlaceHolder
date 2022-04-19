package nz.jing.jsonplaceholder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.databinding.PostDetailFragmentBinding
import nz.jing.jsonplaceholder.model.Result

@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private var _binding : PostDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val post: MutableLiveData<Post?> = MutableLiveData(null)


    private val commentsViewModel: CommentListViewModel by viewModels()
    private val postViewModel: PostListViewModel by viewModels()

    private val args: PostDetailFragmentArgs by navArgs()

    private lateinit var commentListAdapter: CommentListAdapter

    companion object {
        fun newInstance() = PostDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentListAdapter = CommentListAdapter()
        binding.comments.adapter = commentListAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.comments)

        post.observe(viewLifecycleOwner) {
            commentsViewModel.getAllComments()
            it?.let {
                binding.post.postAuthor.text = it.userId.toString()
                binding.post.postBody.text = it.body
                binding.post.postTitle.text = it.title

            }
        }
        observeComments()
        observePosts()
        postViewModel.getPosts()
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postViewModel.posts.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            post.value = result.data.find { it.id == args.postId }
                        }
                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }

    private fun observeComments() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                commentsViewModel.comments.collect { result ->
                    if (post.value == null) {
                        return@collect
                    }
                    when (result) {
                        is Result.Success -> {
                            val comments = result.data.filter { it.postId == post.value!!.id }
                            commentListAdapter.submitList(comments)
                        }
                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }
}