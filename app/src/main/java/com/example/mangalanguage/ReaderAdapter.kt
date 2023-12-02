package com.example.mangalanguage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ItemReaderBinding
import com.example.mangalanguage.view.ReaderActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class ReaderAdapter(private val activity: ReaderActivity, private val readerImageList: List<String>) :
    RecyclerView.Adapter<ReaderAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemReaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(activity: ReaderActivity, reader: String){

            val fab: FloatingActionButton = binding.itemReaderFloatingButton
            val imageView = binding.imageView

            fab.setOnClickListener {
                activity.ImageUrl.postValue(reader)
            }

            Picasso.get()
                .load(reader)
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
        holder.bindItem(activity, reader)
    }

    override fun getItemCount(): Int {
        return readerImageList.size
    }
}