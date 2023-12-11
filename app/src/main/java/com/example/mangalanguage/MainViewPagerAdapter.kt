package com.example.mangalanguage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    //Функция даёт понять, сколько элементов мы используем
    override fun getItemCount(): Int = fragments.size

    //Эта функция позволяет создать фрагмент и инициализировать его
    override fun createFragment(position: Int): Fragment = fragments[position]
}