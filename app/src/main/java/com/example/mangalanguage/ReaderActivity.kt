package com.example.mangalanguage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.databinding.ActivityReaderBinding
import com.google.android.material.appbar.MaterialToolbar

class ReaderActivity : AppCompatActivity() {

    lateinit var binding: ActivityReaderBinding

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ReaderAdapter
    lateinit var readerList: List<ReaderData>
    lateinit var topAppBar: MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

       topAppBar = binding.readerTopAppbar

        recyclerView = binding.readerRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)


        readerList= listOf(
            ReaderData("https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/19.jpeg"),
            ReaderData("https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/20.jpeg"),
            ReaderData("https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/21.jpeg"),
            ReaderData("https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/22.jpeg"),
            ReaderData("https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/23.jpeg")
        )

        adapter = ReaderAdapter(readerList)
        recyclerView.adapter = adapter

        topAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
       }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            //TODO поработать с intent, вроде при таком подходе происходят потери памяти.
            when(menuItem.itemId){
                R.id.reader_goMainActivity -> {
                    val intent = Intent(this, MainActivity2::class.java)
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