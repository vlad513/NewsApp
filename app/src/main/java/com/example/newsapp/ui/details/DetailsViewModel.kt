package com.example.newsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.remote.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository):ViewModel(){


    fun saveFavoriteArticles(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavorite(article = article)
    }
}