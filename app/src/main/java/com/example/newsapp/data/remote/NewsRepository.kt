package com.example.newsapp.data.remote

import retrofit2.http.Query
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        apiService.getHeadlines(countryCode = countryCode, page = pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        apiService.getEverything(query = query, page = pageNumber)
}