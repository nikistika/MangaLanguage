package com.example.mangalanguage.view.manga_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.mangalanguage.R
import com.example.mangalanguage.adapters.manga_search_adapters.ReaderAdapterVP
import com.example.mangalanguage.adapters.manga_search_adapters.VerticalViewPager
import com.example.mangalanguage.databinding.ActivityReader2Binding
import com.example.mangalanguage.interfaces.SendUrlImage
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.example.mangalanguage.view.MainActivity
import com.example.mangalanguage.viewModel.ReaderVPViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Ридер манги, который работает на основе ViewPager
 */

@AndroidEntryPoint
class ReaderActivityVP : AppCompatActivity(), SendUrlImage {

    private val viewModel: ReaderVPViewModel by viewModels()

    lateinit var topAppBar: MaterialToolbar
    lateinit var viewPager: VerticalViewPager
    lateinit var viewPagerAdapter: ReaderAdapterVP
    lateinit var fabTranslate: FloatingActionButton

    private lateinit var mangadexApi: MangaDexApiService

    lateinit var binding: ActivityReader2Binding

    val mangaImageUrl: MutableLiveData<String?> = MutableLiveData()
    val mangaImageUrlResult: LiveData<String?> = mangaImageUrl


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
            viewModel.getMangaImages(idChapter)
        }



        viewModel.mangaImageResult.observe(this, Observer {

            Log.d("MyLog", "getMangaImage: $it")

            val mangaImageList = it.chapter.dataSaver
            val mangaId = it.chapter.hash



            viewPagerAdapter = ReaderAdapterVP(this, mangaImageList, mangaId, this)
            viewPager.adapter = viewPagerAdapter

            })


        mangaImageUrlResult.observe(this, Observer { imageUrl ->
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

    override fun sendUrlImage(imageUrl: String) {
        mangaImageUrl.postValue(imageUrl)
    }
}