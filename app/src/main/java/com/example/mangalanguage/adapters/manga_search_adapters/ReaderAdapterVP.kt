package com.example.mangalanguage.adapters.manga_search_adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mangalanguage.R
import com.example.mangalanguage.interfaces.SendUrlImage
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Адаптер ридера манги, который работает на основе ViewPager
 */

class ReaderAdapterVP(
    private val context: Context,
    private val imageList: List<String>,
    private val mangaId: String,
    private val sendUrlImage: SendUrlImage
) : PagerAdapter(), ViewPager.OnPageChangeListener {

    private var currentPosition: Int = 0

    init {
        if (context is Activity) {
            // Установка слушателя изменения страницы для ViewPager
            (context.findViewById<ViewPager>(R.id.viewPager)).addOnPageChangeListener(this)
        } else {
            // Обработка ситуации, когда context не является экземпляром Activity
            // Можно выкинуть исключение или выполнить другие действия по вашему усмотрению
        }
    }

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
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // в следующем методе мы надуваем нашу настраиваемую
        // файл макета, который мы создали.
        val itemView: View =
            mLayoutInflater.inflate(R.layout.item_reader2, container, false)

        // в следующем методе мы инициализируем
        // наше изображение с помощью идентификатора.
        val photoView: PhotoView = itemView.findViewById<View>(R.id.idIVImage) as PhotoView

        // в следующем методе мы устанавливаем
        // ресурс изображения для изображения.

        val imageUrl = "https://uploads.mangadex.org/data-saver/${mangaId}/${imageList[position]}"

        Picasso.get()
            .load(imageUrl)
            .into(photoView)

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

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val skr = 100 //Заглушка
        //TODO сделать что-нибудь с этими заглушками
    }

    //Метод, предоставляющий доступ к текущему изображению на экране
    override fun onPageSelected(position: Int) {
        currentPosition = position

        // Получение URL текущей картинки
        val imageUrl = "https://uploads.mangadex.org/data-saver/${mangaId}/${imageList[currentPosition]}"
        sendUrlImage.sendUrlImage(imageUrl)
    }

    override fun onPageScrollStateChanged(state: Int) {
        val skr = 100 //Заглушка
    }
}

