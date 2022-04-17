package nz.jing.jsonplaceholder.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import nz.jing.jsonplaceholder.R
import nz.jing.jsonplaceholder.databinding.PostListFragmentBinding

@AndroidEntryPoint
class PostListFragment : Fragment() {
    private var _binding : PostListFragmentBinding? = null
    private val binding get() = _binding!!

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
}