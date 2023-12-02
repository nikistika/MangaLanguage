package com.example.mangalanguage.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mangalanguage.R
import com.example.mangalanguage.ViewPagerAdapter
import com.example.mangalanguage.databinding.ActivityMainBinding
import com.example.mangalanguage.view.main_fragments.CardFragment
import com.example.mangalanguage.view.main_fragments.FavoriteFragment
import com.example.mangalanguage.view.main_fragments.SettingFragment
import com.example.mangalanguage.view.main_fragments.WordsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.mainViewPager
        navigationView = binding.bottomNavigationView

        prepapeViewPager()

        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favoriteManga -> {
                    viewPager.currentItem = 0 // Установка текущего элемента во ViewPager2
                    true
                }
                R.id.cardsManga -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.wordsManga -> {
                    viewPager.currentItem = 2
                    true
                }
                R.id.settingManga -> {
                    viewPager.currentItem = 3
                    true
                }
                else -> false
            }
        }
    }


    private fun prepapeViewPager() {
        val fragmentList = arrayListOf(
            FavoriteFragment.newInstance(),
            CardFragment.newInstance(),
            WordsFragment.newInstance(),
            SettingFragment.newInstance()
        )

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> navigationView.menu.findItem(R.id.favoriteManga).isChecked = true
                    1 -> navigationView.menu.findItem(R.id.cardsManga).isChecked = true
                    2 -> navigationView.menu.findItem(R.id.wordsManga).isChecked = true
                    3 -> navigationView.menu.findItem(R.id.settingManga).isChecked = true
                }
            }
        })
    }

    }
