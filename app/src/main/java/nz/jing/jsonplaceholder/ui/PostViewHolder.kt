package nz.jing.jsonplaceholder.ui

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import nz.jing.jsonplaceholder.data.entity.Post
import nz.jing.jsonplaceholder.databinding.PostListItemBinding

class PostViewHolder(private val binding: PostListItemBinding) : RecyclerView.ViewHolder (binding.root){
    fun bind(item: Post) {
        binding.apply {
            postTitle.text = item.title
            postAuthor.text = item.userId.toString()
            postBody.text = item.body
        }
        binding.root.setOnClickListener {
            val navController = it.findNavController()
            val action = PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(item.id)
            navController.navigate(action)
        }
    }
}