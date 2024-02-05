package com.example.mangalanguage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * ViewPagerAdapter, определябщий работу ViewPager в MainActivity
 */

class MainViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    //Функция даёт понять, сколько элементов мы используем
    override fun getItemCount(): Int = fragments.size

    //Эта функция позволяет создать фрагмент и инициализировать его
    override fun createFragment(position: Int): Fragment = fragments[position]
}