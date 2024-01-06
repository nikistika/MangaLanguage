package com.example.mangalanguage.view.manga_favorite_view

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.tech.MifareUltralight.PAGE_SIZE
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
import com.example.mangalanguage.manga_favorite.manga_info.GoToReader
import com.example.mangalanguage.manga_favorite.ListItem
import com.example.mangalanguage.R
import com.example.mangalanguage.databinding.ActivityMangaInfoBinding
import com.example.mangalanguage.manga_favorite.MangaChapterResult
import com.example.mangalanguage.manga_favorite.MangaInfo
import com.example.mangalanguage.manga_favorite.UpdateListInfo
import com.example.mangalanguage.models.MangaDex.MangaChapters.MangaChapter
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.example.mangalanguage.view.manga_favorite_view.recyclerView.MangaInfoAdapter2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Response

class MangaInfoActivity : AppCompatActivity(), GoToReader, UpdateListInfo {

    lateinit var binding: ActivityMangaInfoBinding

    lateinit var topAppBar: MaterialToolbar
    lateinit var backgroundImageView: ShapeableImageView
    lateinit var readButton: MaterialButton
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: MangaInfoAdapter2

    lateinit var mangaInfoAndChapterList: MutableList<ListItem>

    private lateinit var mangadexApi: MangaDexApiService

    private lateinit var response: Response<MangaChapter>

    private var chaptersRange = 0

    private val mangaChapterLD: MutableLiveData<MangaChapter> = MutableLiveData()
    val mangaChapterLDResult: LiveData<MangaChapter> = mangaChapterLD

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMangaInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topAppBar = binding.topAppbar
        backgroundImageView = binding.backgroundImageView
        readButton = binding.readButton

        recyclerView = binding.chapterRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        mangadexApi = MangaApiClient.getInstance().create(MangaDexApiService::class.java)

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

        lifecycleScope.launch {

            val getMangaChapter = getMangaChapters(mangaId, chaptersRange)
            mangaChapterLD.postValue(getMangaChapter)

        }



        var mangaFlag = true
        mangaInfoAndChapterList = mutableListOf()

        mangaChapterLDResult.observe(this, Observer {

                if (mangaFlag){
                mangaInfoAndChapterList.add(MangaInfo(mangaTitle, mangaDescription, mangaCoverUrl))
                }

            it.data.forEach{ mangaChapter ->

            val chapterResult = MangaChapterResult(
                id = mangaChapter.id,
                chapter = mangaChapter.attributes.chapter,
                title = mangaChapter.attributes.title
            )
                Log.d("MyLog", "chapterResult: $chapterResult")

                mangaInfoAndChapterList.add(chapterResult)
            }

                    if (mangaFlag) {
                        adapter = MangaInfoAdapter2(mangaInfoAndChapterList, this)
                        recyclerView.adapter = adapter
                        mangaFlag = false
                    } else{
                        updateList(mangaInfoAndChapterList)

                    }

        })

//        listenerUpdateList = MangaInfoAdapter2(chapterResultList, this)

        // Флаг, позволяющий запустить функцию в конце списка лишь один раз до обновления
        var isFetching = false
        //Блок кода, позволяюзий вызвать функцию во время достижения конца списка
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Проверка, находится ли RecyclerView в конце и код еще не сработал
                if (!isFetching &&
                    (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE // Убедитесь, что у вас достаточно элементов, чтобы сделать это проверку
                ) {
                    Log.d("MyLog", "End")

                    // Установите флаг, чтобы код больше не сработал
                    isFetching = true

                    lifecycleScope.launch {
                        chaptersRange += 100
                        val getMangaChapter = getMangaChapters(mangaId, chaptersRange)
                        mangaChapterLD.postValue(getMangaChapter)
                        // Сбросите флаг после завершения загрузки
                        isFetching = false
                    }
                }
            }
        })

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
        response = mangadexApi.getMangaChapter(mangaId, offset)
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

    override fun goToReader(id: String) {
        // Реализуйте действия, которые должны быть выполнены при вызове goToReader
        // Например, запуск ReaderActivity
        val intent = Intent(this, ReaderActivity2::class.java)
        intent.putExtra("message_key", id)
        startActivity(intent)
    }

    override fun updateList(newItems: List<ListItem>) {
        adapter.updateList(newItems)
    }
}