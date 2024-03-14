package com.example.newsapp.data.remote

import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNews(countryCode:String,pageNumber: Int) =
        apiService.getHeadlines(countryCode = countryCode, page = pageNumber)

}