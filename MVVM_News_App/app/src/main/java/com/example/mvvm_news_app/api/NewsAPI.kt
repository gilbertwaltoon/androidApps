package com.example.mvvm_news_app.api

import com.example.mvvm_news_app.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.mvvm_news_app.util.Constants.Companion.API_KEY
import retrofit2.Response

interface NewsAPI {

    // This 1st function gets BreakingNews (query restricted to top-headlines)
    // It's a suspend function
    // The GET string part of the  https://newsapi.org/ URL
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country_code: String = "gb",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    // This 2nd function gets Everything (query restricted to top-headlines)
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>



}