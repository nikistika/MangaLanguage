package com.example.mangalanguage.view.manga_favorite_view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.R
import com.example.mangalanguage.manga_favorite.ReaderAdapter
import com.example.mangalanguage.databinding.ActivityReaderBinding
import com.example.mangalanguage.view.MainActivity
import com.google.android.material.appbar.MaterialToolbar

class ReaderActivity : AppCompatActivity() {

    lateinit var binding: ActivityReaderBinding

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ReaderAdapter
    lateinit var readerList: List<String>
    lateinit var topAppBar: MaterialToolbar

    val ImageUrl: MutableLiveData<String> = MutableLiveData()
    //TODO разобраться с безопасным сохранением в LiveData
    //val ImageUrlResult: LiveData<ReaderData> = ImageUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topAppBar = binding.topAppbar

        recyclerView = binding.readerRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)


        readerList= listOf(
            "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/19.jpeg",
            "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/20.jpeg",
            "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/21.jpeg",
            "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/22.jpeg",
            "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/23.jpeg"
        )

        adapter = ReaderAdapter(this, readerList)
        recyclerView.adapter = adapter


        ImageUrl.observe(this, Observer {

            val intent = Intent(this, TranslateActivity::class.java)

            intent.putExtra("message_key", it)
            //TODO Исправить тип листа
            startActivity(intent)

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
}