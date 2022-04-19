package nz.jing.jsonplaceholder.ui

import androidx.recyclerview.widget.RecyclerView
import nz.jing.jsonplaceholder.data.entity.Comment
import nz.jing.jsonplaceholder.databinding.CommentListItemBinding

class CommentViewHolder(private val binding: CommentListItemBinding) : RecyclerView.ViewHolder (binding.root){
    fun bind(pos: Int, item: Comment) {
        binding.apply {
            email.text = item.email
            author.text = item.name
            body.text = item.body
            id.text = "COMMENT ${pos + 1}"
        }
    }
}