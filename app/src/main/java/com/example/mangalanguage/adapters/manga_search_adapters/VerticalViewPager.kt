package com.example.mangalanguage.adapters.manga_search_adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Класс, переопределящий работу ViewPager в ReaderActivityVP
 */

class VerticalViewPager (context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    init {
        setPageTransformer(true, VerticalPageTransformer())
    }

    private inner class VerticalPageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.width
            val pageHeight = view.height
            view.alpha = 1f
            view.translationX = pageWidth * -position
            view.translationY = position * pageHeight
            view.scaleX = 1f
            view.scaleY = 1f
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return intercepted
    }

    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val x = ev.x
        val y = ev.y
        ev.setLocation(y / height * width, x / width * height)
        return ev
    }
}