package nz.jing.jsonplaceholder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nz.jing.jsonplaceholder.databinding.PostListFragmentBinding
import nz.jing.jsonplaceholder.model.Result

@AndroidEntryPoint
class PostListFragment : Fragment() {
    private var _binding : PostListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var postAdapter: PostListAdapter

    companion object {
        fun newInstance() = PostListFragment()
    }

    private val viewModel: PostListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostListAdapter()
        binding.posts.adapter = postAdapter
        binding.swipeRefresh.setOnRefreshListener { getPosts() }
        observePosts()
        getPosts()
    }

    private fun getPosts() {
        viewModel.getPosts()
        showLoading(postAdapter.currentList.isEmpty())
    }

    private fun showLoading(loading: Boolean) {
        binding.swipeRefresh.isRefreshing = loading
        binding.networkStatus.visibility = if(loading) View.VISIBLE else View.INVISIBLE
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            result.data.let { postAdapter.submitList(it) }
                            showLoading(false)
                        }
                        is Result.Error -> {
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }
}