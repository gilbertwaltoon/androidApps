package com.example.mvvm_news_app.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_news_app.repository.NewsRepository

// "can't use constructors for viewmodels. Instead  need to make a
// view model provider factory - we'll call this NewsViewModelProviderFactory

class NewsViewModelProviderFactory(val newsRepository : NewsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}