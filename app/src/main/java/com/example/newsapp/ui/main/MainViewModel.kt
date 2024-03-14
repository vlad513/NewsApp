package com.example.newsapp.ui.main


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
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

     val newsLivedata: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
     var newsPage = 1

    init {
        getNews(countryCode = "ru")
    }

    private fun getNews(countryCode: String) =
        viewModelScope.launch {
            newsLivedata.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsLivedata.postValue(Resource.Success(res))
                }
            } else {
                newsLivedata.postValue(Resource.Error(message = response.message()))
            }

        }
}