package com.example.mvvm_news_app.repository

import com.example.mvvm_news_app.api.RetrofitInstance
import com.example.mvvm_news_app.db.ArticleDataBase
import com.example.mvvm_news_app.models.Article
import retrofit2.Retrofit

class NewsRepository (
    val db : ArticleDataBase
    ) {

    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery : String, pageNumber : Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article : Article) = db.getArticleDao().upsertDB(article)

    fun getSavedNews = db.getArticleDao().getAllArticlesFromDB()

    suspend fun deleteArticle(article:Article) = db.getArticleDao().deleteArticleFromB(article)


}