package com.example.mangalanguage.view.main_fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mangalanguage.databinding.FragmentCardBinding
import com.example.mangalanguage.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент, на котором происходит изучение слов с помощью карточек
 */

@AndroidEntryPoint
class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_forecast, container, false)
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    companion object {
        fun newInstance() = CardFragment()
    }
 }


