package com.example.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.data.remote.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TestRepository) : ViewModel() {

    private val _all = MutableLiveData<NewsResponse>()
    val all: LiveData<NewsResponse>
        get() = _all

    init {
        getAll()
    }

    fun getAll() = viewModelScope.launch {
        repository.getAll().let {
            if(it.isSuccessful){
                _all.postValue(it.body())
            }else{
                Log.d("check_data","Failed to load articles :${it.errorBody()}")
            }
        }
    }
}