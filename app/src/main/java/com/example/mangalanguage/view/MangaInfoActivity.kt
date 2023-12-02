package com.example.mangalanguage.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.MangaInfoAdapter
import com.example.mangalanguage.R
import com.example.mangalanguage.databinding.ActivityMangaInfoBinding
import com.example.mangalanguage.models.MangaDex.MangaChapterResult
import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData
import com.example.mangalanguage.models.chapterList
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MangaInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityMangaInfoBinding

    lateinit var topAppBar: MaterialToolbar
    lateinit var backgroundImageView: ShapeableImageView
    lateinit var topImageView: ShapeableImageView
    lateinit var mangaTextTitle: MaterialTextView
    lateinit var mangaTextDescription: MaterialTextView
    lateinit var readButton: MaterialButton
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MangaInfoAdapter

    lateinit var chapterResultList: MutableList<MangaChapterResult>

    private lateinit var mangadexApi: MangaDexApiService

    private val mangaChapterLD: MutableLiveData<MangaChapter> = MutableLiveData()
    val mangaChapterLDResult: LiveData<MangaChapter> = mangaChapterLD

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMangaInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topAppBar = binding.topAppbar
        backgroundImageView = binding.backgroundImageView
        topImageView = binding.topImageView
        mangaTextTitle = binding.mangaTextTitle
        mangaTextDescription = binding.mangaTextDescription
        readButton = binding.readButton
        bottomNavigationView = binding.bottomNavigationView

        recyclerView = binding.chapterRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        mangadexApi = MangaApiClient.getInstance().create(MangaDexApiService::class.java)


//        if (intent != null) {
            val mangaId = intent.getStringExtra("mangaId")
            Log.d("MyLog", "mangaId: $mangaId")

        val mangaTitle = intent.getStringExtra("mangaTitle")
            val mangaDescription = intent.getStringExtra("mangaDescription")
            val mangaYear = intent.getStringExtra("mangaYear")
            //TODO Добавить год и автора
            val mangaCoverUrl = intent.getStringExtra("mangaCoverUrl")

            Picasso.get()
                .load(mangaCoverUrl)
                .into(backgroundImageView)

            Picasso.get()
                .load(mangaCoverUrl)
                .into(topImageView)

            mangaTextTitle.text = mangaTitle
            mangaTextDescription.text = mangaDescription

//        }

        lifecycleScope.launch {
            val getMangaChapter = getMangaChapters(mangaId, 0)
            Log.d("MyLog", "getMangaChapter: $getMangaChapter")
            mangaChapterLD.postValue(getMangaChapter)
        }

        chapterResultList = mutableListOf()

        mangaChapterLDResult.observe(this, Observer {

            it.data.forEach{ mangaChapter ->

            val chapterResult = MangaChapterResult(
                id = mangaChapter.id,
                chapter = mangaChapter.attributes.chapter,
                title = mangaChapter.attributes.title,
                publishAt = mangaChapter.attributes.publishAt
            )

                chapterResultList.add(chapterResult)
                Log.d("MyLog", "chapterResultList: $chapterResultList")
            }

            adapter = MangaInfoAdapter(chapterResultList)
            recyclerView.adapter = adapter

        })

        val chapterList = listOf<chapterList>(
            chapterList("Глава 1", "25.11.1998"),
            chapterList("Глава 2", "25.11.1998"),
            chapterList("Глава 3", "25.11.1998"),
            chapterList("Глава 4", "25.11.1998"),
            chapterList("Глава 5", "25.11.1998"),
            chapterList("Глава 6", "25.11.1998"),
            chapterList("Глава 7", "25.11.1998"),
            chapterList("Глава 8", "25.11.1998"),
            chapterList("Глава 9", "25.11.1998"),
            chapterList("Глава 10", "25.11.1998")
        )



        topAppBar.setNavigationOnClickListener {
            finish()
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            //TODO поработать с intent, вроде при таком подходе происходят потери памяти.
            when(menuItem.itemId){
                R.id.reader_goMainActivity -> {
                    val intent = Intent(this, ReaderActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    suspend fun getMangaChapters(mangaId: String?, offset: Int): MangaChapter {
        val response = mangadexApi.getMangaChapter(mangaId, offset)
        if (response.isSuccessful){
            val mangaChapterResult = response.body()
            if (mangaChapterResult != null){
                return MangaChapter(
                    data = mangaChapterResult.data,
                    limit = mangaChapterResult.limit,
                    offset = mangaChapterResult.offset,
                    response = mangaChapterResult.response,
                    result = mangaChapterResult.result,
                    total = mangaChapterResult.total
                )
            }
        }
        return MangaChapter(
            data = emptyList(),
            limit = 0,
            offset = 0,
            response = "",
            result = "",
            total = 0
        )
    }
}