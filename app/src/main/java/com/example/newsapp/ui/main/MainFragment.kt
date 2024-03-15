package com.example.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.newsLivedata.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success -> {
                    progress_bar.visibility = View.INVISIBLE
                    response.data?.let { newsAdapter.differ.submitList(it.articles) }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData","mainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }

        }

    }

    private fun initAdapter(){
        newsAdapter = NewsAdapter()
        recycler_view.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}