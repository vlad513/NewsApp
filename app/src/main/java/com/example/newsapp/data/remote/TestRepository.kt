package com.example.newsapp.data.remote

import javax.inject.Inject

class TestRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAll() = apiService.getHeadlines()
}