package com.example.mangalanguage.adapters.manga_search_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ItemReaderBinding
import com.example.mangalanguage.view.manga_activity.ReaderActivityRV
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

/**
 * Адаптер ридера манги, который работает на основе RecyclerView
 */

class ReaderAdapterRV(private val activity: ReaderActivityRV, private val readerImageList: List<String>) :
    RecyclerView.Adapter<ReaderAdapterRV.ViewHolder>() {

    //TODO С помощью gpt улучшить код, подправить параметры

    class ViewHolder(private val binding: ItemReaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(activity: ReaderActivityRV, reader: String) {

            val fab: FloatingActionButton = binding.itemReaderFloatingButton
            val imageView = binding.imageView

            fab.setOnClickListener {
                activity.ImageUrl.postValue(reader)
                //TODO сделать связь через интерфейс
            }

            Picasso.get()
                .load(reader)
                .into(imageView)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
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