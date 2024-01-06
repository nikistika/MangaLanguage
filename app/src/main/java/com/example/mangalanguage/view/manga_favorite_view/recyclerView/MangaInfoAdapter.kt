package com.example.mangalanguage.view.manga_favorite_view.recyclerView

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.manga_favorite.MangaChapterResult
import com.example.mangalanguage.databinding.ItemChapterBinding
import com.example.mangalanguage.view.manga_favorite_view.MangaInfoActivity

class MangaInfoAdapter (
    private val chapterList: MutableList<MangaChapterResult>,
    private val mangaInfoActivity: MangaInfoActivity
): RecyclerView.Adapter<MangaInfoAdapter.ViewHolder>() {

    var number = 1

    class ViewHolder(private val binding: ItemChapterBinding
    ) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindItem(mangaItem: MangaChapterResult, activity: MangaInfoActivity) {

            val cardViewItem = binding.cardViewItem

            val chapterNumber = binding.chapterNumber
            val chapterTitle = binding.chapterTitle

            chapterNumber.text = "Глава ${mangaItem.chapter}."
            chapterTitle.text = mangaItem.title

//            cardViewItem.setOnClickListener {
//                activity.idChapterLDResult.postValue(mangaItem.id)
//            }

        }

    }

//    fun addItems(newItems: List<MangaChapterResult>) {
//        val startPosition = chapterList.size
//        chapterList.addAll(newItems)
//        //обновление вьюшки recyclerview
//        notifyItemRangeInserted(startPosition, newItems.size)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("onCreateLog", "onCreateViewHolder $number")
        number += 1
        val binding = ItemChapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapterItem = chapterList[position]
        holder.bindItem(chapterItem, mangaInfoActivity)
    }
}