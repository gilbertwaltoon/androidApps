package com.example.mvvm_news_app.db

import androidx.room.TypeConverter
import com.example.mvvm_news_app.models.Source

class Converters {

    // Room can't store complex objects like 'Source'
    // which is a returned java 'object'
    // We need to tell it how to convert a Source obj. to Strings
    @TypeConverter
    fun fromSource(source: Source):String {
        return source.name
    }

    @TypeConverter
    fun toSource(name:String) : Source {
        return Source(name,name)
    }
}