package com.example.mvvm_news_app.api

import com.example.mvvm_news_app.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    // by lzay will do late and prevent slow startup when the app needs to create a lot of objects
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
            // create(Class<T> service)
           // Create an implementation of the API endpoints defined by the service interface
          //            @GET("v2/top-headlines")
            //          suspend fun getBreakingNews(
            //          @Query("country")
            //          country_code: String = "gb",
        }
    }
}