package com.example.mangalanguage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ItemReaderBinding
import com.squareup.picasso.Picasso

class ReaderAdapter(private val readerImageList: List<ReaderData>) :
    RecyclerView.Adapter<ReaderAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemReaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(reader: ReaderData){

            val imageView = binding.imageView

            Picasso.get()
                .load(reader.imageUrl)
                .into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReaderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reader = readerImageList[position]
        holder.bindItem(reader)
    }

    override fun getItemCount(): Int {
        return readerImageList.size
    }
}