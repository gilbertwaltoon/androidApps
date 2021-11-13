package com.example.mvvm_news_app.ui.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm_news_app.R
import com.example.mvvm_news_app.app.ui.NewsViewModel
import com.example.mvvm_news_app.app.ui.NewsViewModelProviderFactory
import com.example.mvvm_news_app.databinding.ActivityNewsBinding
import com.example.mvvm_news_app.db.ArticleDataBase
import com.example.mvvm_news_app.repository.NewsRepository


class NewsActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityNewsBinding
    lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val newsRepository = NewsRepository(ArticleDataBase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View binding deprecated https://www.youtube.com/watch?v=MXZz438aCDM  and
        // https://stackoverflow.com/questions/59275009/fragmentcontainerview-using-findnavcontroller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}