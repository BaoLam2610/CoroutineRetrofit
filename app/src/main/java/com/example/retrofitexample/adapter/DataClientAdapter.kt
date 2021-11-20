package com.example.retrofitexample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.ItemDataClientBinding
import com.example.retrofitexample.model.DataClient

class DataClientAdapter(
    var dataClientList: List<DataClient>,
) : RecyclerView.Adapter<DataClientAdapter.DataClientViewHolder>() {

    inner class DataClientViewHolder(var binding: ItemDataClientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dataClient: DataClient) {
            with(binding) {
                tvId.text = "Id: " + dataClient.id.toString()
                tvUserId.text = "UserId: " + dataClient.userId.toString()
                tvTitle.text = "Title: " + dataClient.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataClientViewHolder {
        val binding = ItemDataClientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return DataClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataClientViewHolder, position: Int) =
        holder.bind(dataClientList[position])

    override fun getItemCount(): Int = dataClientList.size


}