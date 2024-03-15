package com.example.newsapp.data.remote

import com.example.newsapp.data.models.Article
import com.example.newsapp.data.storage.ArticleDao
import retrofit2.http.Query
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService,private val articleDao: ArticleDao) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        apiService.getHeadlines(countryCode = countryCode, page = pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        apiService.getEverything(query = query, page = pageNumber)


    fun getFavoriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavorite(article:Article) = articleDao.insert(article = article)

    suspend fun deleteFromFavorite(article:Article) = articleDao.delete(article = article)
}