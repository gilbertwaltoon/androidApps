package com.example.mvvm_news_app.ui.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import com.example.mvvm_news_app.R
import com.example.mvvm_news_app.app.ui.NewsViewModel

class ArticleNewsFragment: Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleNewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article

       // https://www.youtube.com/watch?v=SlOTIcDQOqI&list=PLQkwcJG4YTCRF8XiCRESq1IFFW8COlxYJ&index=11
       // 7.07
       // webView.apply{
       //     webViewClient = WebViewClient()
       //     loadURL(article.url)
        }
    }
}