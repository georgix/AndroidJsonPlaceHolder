package nz.jing.jsonplaceholder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import nz.jing.jsonplaceholder.databinding.PostDetailFragmentBinding

class PostDetailFragment : Fragment() {
    private var _binding : PostDetailFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PostDetailFragment()
    }

    private val viewModel: CommentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}