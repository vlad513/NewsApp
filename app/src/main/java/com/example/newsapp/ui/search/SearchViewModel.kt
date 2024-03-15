package com.example.newsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.data.remote.NewsRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val searchLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewPage = 1

    init {
        getSearchNews(query = "")
    }

    fun getSearchNews(query: String) =
        viewModelScope.launch {
            searchLiveData.postValue(Resource.Loading())
            val response = repository.getSearchNews(query = query, pageNumber = searchNewPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchLiveData.postValue(Resource.Success(data = res))
                }
            } else {
                searchLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

}