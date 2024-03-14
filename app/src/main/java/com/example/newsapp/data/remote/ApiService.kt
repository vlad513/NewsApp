package com.example.newsapp.data.remote


import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("api_Key") apiKey: String = API_KEY
    )


    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String = "ru",
        @Query("page") page: Int = 1,
        @Query("api_Key") apiKey: String = API_KEY
    )
}