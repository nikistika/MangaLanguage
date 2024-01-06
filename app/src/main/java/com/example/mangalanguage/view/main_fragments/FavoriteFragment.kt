package com.example.mangalanguage.view.main_fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.manga_favorite.FavoriteAdapter
import com.example.mangalanguage.databinding.FragmentFavoriteBinding
import com.example.mangalanguage.models.MangaDex.MangaData.MangaData
import com.example.mangalanguage.models.MangaDex.MangaDataResult
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    //TODO ПОСМОТРИ ОТВЕТ ОТ GPT

    private var _binding: FragmentFavoriteBinding?= null
    private val binding get() = _binding

    private lateinit var adapter: FavoriteAdapter
    private lateinit var searchView: SearchView

    lateinit var mangaResultList: MutableList<MangaDataResult?>


    private lateinit var mangadexApi: MangaDexApiService

    private val mangaInfo: MutableLiveData<MangaData> = MutableLiveData()
    val mangaInfoResult: LiveData<MangaData> = mangaInfo


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        //TODO почитать про жизненные циклы и мб перекинуть код в другой метод
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("MyLog", "Start Activity")
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        mangadexApi = MangaApiClient.getInstance().create(MangaDexApiService::class.java)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.isAutoMeasureEnabled
        binding?.favoriteRecyclerview?.layoutManager = layoutManager

        searchView = binding?.searchView as SearchView

        mangaResultList = mutableListOf()

        mangaInfoResult.observe(viewLifecycleOwner, Observer {

                //forEach - функция-цикл, которая применяет функцию (в данном случае add) к каждому элементу списка)
            it.data.forEach { mangaItem ->
                val coverArtRelationship = mangaItem.relationships.firstOrNull { it.type == "cover_art" }
                val fileName = coverArtRelationship?.attributes?.fileName

                val mangaResult = MangaDataResult(
                    title = mangaItem.attributes.title,
                    year = mangaItem.attributes.year.toString(),
                    description = mangaItem.attributes.description,
                    id = mangaItem.id,
                    fileName = fileName
                )

                mangaResultList.add(mangaResult)
            }

            if (mangaResultList.isNotEmpty()) {
                        adapter = FavoriteAdapter(mangaResultList)
                        binding?.favoriteRecyclerview?.adapter = adapter
                    }

        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                lifecycleScope.launch {
                    val mangaData = getMangaData(query)
                    mangaResultList = mutableListOf()
                    mangaInfo.postValue(mangaData)
                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {



                return false
            }
        })


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    suspend fun getMangaData(title: String): MangaData {
        val response = mangadexApi.getMangaData(title)
        if (response.isSuccessful){
            val mangaDataResult = response.body()
            if (mangaDataResult != null){
                return MangaData(
                    data = mangaDataResult.data,
                    limit = mangaDataResult.limit,
                    offset = mangaDataResult.offset,
                    response = mangaDataResult.response,
                    result = mangaDataResult.result,
                    total = mangaDataResult.total
                )
            }
        }
        return MangaData(
            `data` = emptyList(),
            limit = 0,
            offset = 0,
            response = "",
            result = "",
            total = 0
        )
    }


    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
