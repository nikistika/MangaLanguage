package com.example.mangalanguage

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ItemChapterBinding
import com.example.mangalanguage.models.MangaDex.MangaChapterResult
import com.example.mangalanguage.models.chapterList

class MangaInfoAdapter (
    private val chapterList: List<MangaChapterResult>
    ): RecyclerView.Adapter<MangaInfoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemChapterBinding
    ) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindItem(mangaItem: MangaChapterResult) {

            val chapterNumber = binding.chapterNumber
            val chapterTitle = binding.chapterTitle

            chapterNumber.text = "Глава ${mangaItem.chapter}."
            chapterTitle.text = mangaItem.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
        holder.bindItem(chapterItem)
    }
}