package com.example.newsapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.data.remote.NewsRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: NewsRepository) :ViewModel(){

    val newsLivedata: MutableLiveData<List<Article>> = MutableLiveData()

    init {
        getSavedArticles()
    }

    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
       newsLivedata.postValue(repository.getFavoriteArticles())
    }

}