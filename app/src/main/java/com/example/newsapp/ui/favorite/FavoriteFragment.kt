package com.example.newsapp.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.main.MainViewModel
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val viewModel by viewModels<FavoriteViewModel>()
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()


        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(R.id.action_favoriteFragment_to_detailsFragment,bundle)
        }

        viewModel.newsLivedata.observe(viewLifecycleOwner){response->
                    response.let { newsAdapter.differ.submitList(it) }

        }

    }

    private fun initAdapter(){
        newsAdapter = NewsAdapter()
        recycler_favorite.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}