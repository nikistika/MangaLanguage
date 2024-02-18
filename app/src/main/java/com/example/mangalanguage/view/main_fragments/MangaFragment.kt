package com.example.mangalanguage.view.main_fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangalanguage.adapters.manga_search_adapters.MangaSearchAdapterRV
import com.example.mangalanguage.databinding.FragmentMangaBinding
import com.example.mangalanguage.models.MangaDex.MangaDataResult
import com.example.mangalanguage.network.MangaApiClient
import com.example.mangalanguage.network.MangaDexApiService
import com.example.mangalanguage.viewModel.MangaFragmentViewModel
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Фрагмент, отвечающий за поиск манги
 */

@AndroidEntryPoint
class MangaFragment : Fragment() {

    //TODO ПОСМОТРИ ОТВЕТ ОТ GPT

    private val viewModel: MangaFragmentViewModel by activityViewModels()

    private var _binding: FragmentMangaBinding?= null
    private val binding get() = _binding

    private lateinit var adapter: MangaSearchAdapterRV
    private lateinit var searchView: SearchView

    private lateinit var mangaResultList: MutableList<MangaDataResult?>

    private lateinit var mangaDexApi: MangaDexApiService



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        //TODO почитать про жизненные циклы и мб перекинуть код в другой метод
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMangaBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mangaDexApi = MangaApiClient.getInstance().create(MangaDexApiService::class.java)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 1)
        layoutManager.isAutoMeasureEnabled
        binding?.favoriteRecyclerview?.layoutManager = layoutManager

        searchView = binding?.searchView as SearchView

        mangaResultList = mutableListOf()

        viewModel.mangaInfoResult.observe(viewLifecycleOwner, Observer {

            //forEach - функция-цикл, которая применяет функцию (в данном случае add) к каждому элементу списка)
            it.data.forEach { mangaItem ->
                val coverArtRelationship = mangaItem.relationships.firstOrNull { it -> it.type == "cover_art" }
                val fileName = coverArtRelationship?.attributes?.fileName

                val authorRelationship = mangaItem.relationships.firstOrNull { it.type == "author" }
                val authorName = authorRelationship?.attributes?.name

                val mangaResult = MangaDataResult(
                    title = mangaItem.attributes.title,
                    year = mangaItem.attributes.year.toString(),
                    author = authorName,
                    description = mangaItem.attributes.description,
                    id = mangaItem.id,
                    fileName = fileName
                )

                mangaResultList.add(mangaResult)
            }

            if (mangaResultList.isNotEmpty()) {
                adapter = MangaSearchAdapterRV(mangaResultList)
                binding?.favoriteRecyclerview?.adapter = adapter
            }

        })


//        lifecycleScope.launch {
//            viewModel.getMangaList("Наруто")
//            mangaResultList = mutableListOf()
//        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                lifecycleScope.launch {
                    viewModel.getMangaList(query)
                    mangaResultList = mutableListOf()
                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }


    companion object {
        fun newInstance() = MangaFragment()
    }
}
