package com.example.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.query
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var newsAdapter: NewsAdapter
    private val viewModel by viewModels<SearchViewModel>()
    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        var job: Job? = null

        ed_search.addTextChangedListener{text:Editable?->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }



        viewModel.searchLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    pag_search_progress_bar.visibility = View.INVISIBLE
                    response.data?.let { newsAdapter.differ.submitList(it.articles) }
                }
                is Resource.Error -> {
                    pag_search_progress_bar.visibility = View.INVISIBLE
                    response.data?.let { error ->
                        Log.e("checkData", "mainFragment: error: ${error}")
                    }
                }
                is Resource.Loading -> {
                    pag_search_progress_bar.visibility = View.VISIBLE
                }
            }

        }

    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        rc_view.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
