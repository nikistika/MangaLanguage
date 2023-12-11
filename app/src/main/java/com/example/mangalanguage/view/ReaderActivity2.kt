package com.example.mangalanguage.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.mangalanguage.R
import com.example.mangalanguage.ReaderViewPagerAdapter
import com.example.mangalanguage.VerticalViewPager
import com.example.mangalanguage.databinding.ActivityReader2Binding
import com.example.mangalanguage.models.MangaDex.MangaImage.Chapter
import com.example.mangalanguage.models.MangaDex.MangaImage.MangaImage
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ReaderActivity2 : AppCompatActivity() {

    lateinit var topAppBar: MaterialToolbar
    lateinit var viewPager: VerticalViewPager
    lateinit var viewPagerAdapter: ReaderViewPagerAdapter
    lateinit var fabTranslate: FloatingActionButton



    private lateinit var mangadexApi: MangaDexApiService

    lateinit var binding: ActivityReader2Binding

    val mangaImageLD: MutableLiveData<MangaImage> = MutableLiveData()
    val mangaImageLDResult: LiveData<MangaImage> = mangaImageLD

    val mangaImageUrlLD: MutableLiveData<String?> = MutableLiveData()
    val mangaImageUrlLDResult: LiveData<String?> = mangaImageUrlLD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReader2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        topAppBar = binding.topAppbar
        viewPager = binding.viewPager
        fabTranslate = binding.fbTranslate

        mangadexApi = MangaApiClient.getInstance().create(MangaDexApiService::class.java)


        val idChapter = intent.getStringExtra("message_key")
        Log.d("MyLog", "idChapter: $idChapter")

        lifecycleScope.launch {
            val getMangaImage = getMangaImage(idChapter)
            mangaImageLD.postValue(getMangaImage)
        }



        mangaImageLDResult.observe(this, Observer {

            Log.d("MyLog", "getMangaImage: $it")

            val mangaImageList = it.chapter.dataSaver
            val mangaId = it.chapter.hash



            viewPagerAdapter = ReaderViewPagerAdapter(this, mangaImageList, mangaId)
            viewPager.adapter = viewPagerAdapter

            })




        val imageList: ArrayList<String> = ArrayList(2)

        mangaImageUrlLDResult.observe(this, Observer { imageUrl ->
            imageUrl?.let {

                fabTranslate.setOnClickListener {
                    val intent = Intent(this, TranslateActivity::class.java)
                    Log.d("MyLog", "url: $imageUrl")
                    intent.putExtra("message_key", imageUrl)
                    startActivity(intent)
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    suspend fun getMangaImage (idChapter: String?): MangaImage{
        val response = mangadexApi.getMangaImage(idChapter)
        if (response.isSuccessful){
            val mangaImageResult = response.body()
            if (mangaImageResult != null){
                return MangaImage(
                    baseUrl = mangaImageResult.baseUrl,
                    chapter = mangaImageResult.chapter,
                    result = mangaImageResult.result
                )
            }
        }
        return MangaImage(
            baseUrl = "",
            chapter = Chapter(emptyList(),emptyList(),""),
            result = ""
        )
    }
}