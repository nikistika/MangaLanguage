package com.example.mangalanguage.adapters.manga_search_adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ItemFavoriteBinding
import com.example.mangalanguage.models.MangaDex.MangaDataResult
import com.example.mangalanguage.view.manga_activity.MangaInfoActivity
import com.squareup.picasso.Picasso

/**
 * Адаптер RecyclerView, отвечающий за работу MangaSearchFragment
 */

class MangaSearchAdapterRV(
    private val mangaList: List<MangaDataResult?>,
) :
    RecyclerView.Adapter<MangaSearchAdapterRV.ViewHolder>(){

    class ViewHolder(private val binding: ItemFavoriteBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(mangaList: MangaDataResult?) {

            val mangaTitleView = binding.mangaTitle
            val mangaYearView = binding.mangaYear
            val mangaCover = binding.mangaImage
            val mangaItemView = binding.cardViewItem

            // URL изображения для загрузки
            val mangaId = mangaList?.id
            val mangaFIleName = mangaList?.fileName
            val imageUrl = "https://uploads.mangadex.org/covers/$mangaId/$mangaFIleName"
            val mangaTitle = mangaList?.title?.en
            val mangaYear = mangaList?.year
            val mangaDescription = mangaList?.description?.en

            // Используем Picasso для загрузки и отображения изображения
            Picasso.get()
                .load(imageUrl)
                .into(mangaCover)


            mangaTitleView.text = mangaTitle
            mangaYearView.text = mangaYear

            mangaItemView.setOnClickListener {

                val intent = Intent(mangaItemView.context, MangaInfoActivity::class.java)

                intent.putExtra("mangaId", mangaId)
                intent.putExtra("mangaTitle", mangaTitle)
                intent.putExtra("mangaDescription", mangaDescription)
                intent.putExtra("mangaYear", mangaYear)
                intent.putExtra("mangaCoverUrl", imageUrl)

                mangaItemView.context.startActivity(intent)

            }

        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mangaItem = mangaList[position]
        holder.bindItem(mangaItem)

        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        holder.itemView.layoutParams = layoutParams
    }
}