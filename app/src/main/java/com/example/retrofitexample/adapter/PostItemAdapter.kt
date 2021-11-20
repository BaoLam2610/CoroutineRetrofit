package com.example.retrofitexample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.ItemDataClientBinding
import com.example.retrofitexample.model.DataClient
import com.example.retrofitexample.model.PostItem

class PostItemAdapter(
    var postList: List<PostItem>,
) : RecyclerView.Adapter<PostItemAdapter.PostItemViewHolder>() {

    inner class PostItemViewHolder(var binding: ItemDataClientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(post: PostItem) {
            with(binding) {
                tvId.text = "Id: " + post.id.toString()
                tvUserId.text = "PostId: " + post.postId.toString()
                tvTitle.text = "Email: " + post.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemDataClientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) =
        holder.bind(postList[position])

    override fun getItemCount(): Int = postList.size


}