package com.example.mvvm_news_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm_news_app.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDB(article : Article) : Long

    // not a suspend 'cos returns a LiveData (LD doesn't work with suspend)
    @Query("SELECT * FROM articles_table")
    fun getAllArticlesFromDB() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticleFromB (article: Article)

}