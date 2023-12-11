package com.example.mangalanguage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.mangalanguage.view.ReaderActivity2
import com.squareup.picasso.Picasso
import java.util.*

class ReaderViewPagerAdapter(
    private val activity: ReaderActivity2,
    private val imageList: List<String>,
    private val mangaId: String) : PagerAdapter() {

    // getCount для возврата размера списка.
    override fun getCount(): Int {
        return imageList.size
    }

    // в следующем методе мы возвращаем объект
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    // в следующем методе мы инициализируем
    // наш элемент и надуваем наш файл макета
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // в следующем методе мы инициализируем
        // нашу службу макета.
        val mLayoutInflater =
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // в следующем методе мы надуваем нашу настраиваемую
        // файл макета, который мы создали.
        val itemView: View =
            mLayoutInflater.inflate(R.layout.item_reader2, container, false)

        // в следующем методе мы инициализируем
        // наше изображение с помощью идентификатора.
        val imageView: ImageView = itemView.findViewById<View>(R.id.idIVImage) as ImageView

        // в следующем методе мы устанавливаем
        // ресурс изображения для изображения.

        val imageUrl = "https://uploads.mangadex.org/data-saver/${mangaId}/${imageList[position]}"
        activity.mangaImageUrlLD.postValue(imageUrl)

        Picasso.get()
            .load(imageUrl)
            .into(imageView)

        // в следующем методе мы добавляем этот
        // элемент представления к контейнеру.
        Objects.requireNonNull(container).addView(itemView)

        // в следующем методе мы просто
        // возвращаем наше представление элемента.
        return itemView
    }

    // в следующем методе мы создаем метод уничтожения элемента.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // в следующем методе мы удаляем представление
        container.removeView(`object` as RelativeLayout)
    }

}

