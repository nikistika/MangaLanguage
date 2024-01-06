package com.example.mangalanguage.view.manga_favorite_view.recyclerView

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.manga_favorite.manga_info.GoToReader
import com.example.mangalanguage.manga_favorite.ListItem
import com.example.mangalanguage.manga_favorite.MangaChapterResult
import com.example.mangalanguage.manga_favorite.MangaInfo
import com.example.mangalanguage.R
import com.example.mangalanguage.manga_favorite.UpdateListInfo
import com.example.mangalanguage.databinding.ItemChapterBinding
import com.example.mangalanguage.databinding.ItemInfoBinding
import com.squareup.picasso.Picasso

class MangaInfoAdapter2(
    private var items: List<ListItem>,
    private val goToReaderListener: GoToReader
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<ListItem>) {
        Log.d("MyLog", "updateList")
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }


    class MangaInfoViewHolder(private val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(MangaInfoItem: MangaInfo) {
            // Привязка данных к Type1ViewHolder через binding
            val imageTop = binding.topImageView
            val textTitle = binding.mangaTextTitle
            val textDescription = binding.mangaTextDescription

            Picasso.get()
                .load(MangaInfoItem.image)
                .into(imageTop)

            textTitle.text = MangaInfoItem.name
            textDescription.text = MangaInfoItem.description
        }
    }

    class MangaChapterViewHolder(private val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(MangaChapterItem: MangaChapterResult, goToReaderListener: GoToReader) {

            val chapterNumber = binding.chapterNumber
            val chapterTitle = binding.chapterTitle
            val itemView = binding.cardViewItem
            val chapterId = MangaChapterItem.id

            chapterNumber.text = "Глава ${MangaChapterItem.chapter}"
            chapterTitle.text = MangaChapterItem.title

            itemView.setOnClickListener {
                Log.d("MyLog", "setOnClickListener: $chapterId")
                goToReaderListener.goToReader(chapterId)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_info -> {
                val binding = ItemInfoBinding.inflate(inflater, parent, false)
                MangaInfoViewHolder(binding)
            }
            R.layout.item_chapter -> {
                val binding = ItemChapterBinding.inflate(inflater, parent, false)
                MangaChapterViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is MangaInfo -> (holder as MangaInfoViewHolder).bind(item)
            is MangaChapterResult -> (holder as MangaChapterViewHolder).bind(item, goToReaderListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun getItemCount(): Int {
        return items.size
    }

}