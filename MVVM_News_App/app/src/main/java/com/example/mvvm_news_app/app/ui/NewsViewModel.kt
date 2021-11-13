package com.example.mvvm_news_app.app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_news_app.models.Article
import com.example.mvvm_news_app.models.NewsResponse
import com.example.mvvm_news_app.repository.NewsRepository
import com.example.mvvm_news_app.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


// "can't use constructors for viewmodels. Instead  need to make a
// view model provider factory - we'll call this NewsViewModelProviderFactory

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews :  <Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("gb")
    }

    // the repository call here is a suspend function, so here we make a co-routine
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handlesearchNewsResponse(response))
    }

    // Response is a retrofit object (no to be confused with our util class Resource)
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>)
            : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // Response is a retrofit object (no to be confused with our util class Resource)
    private fun handlesearchNewsResponse(response: Response<NewsResponse>)
            : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) =viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedArticleNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch{
        newsRepository.deleteArticle(article)
    }


}