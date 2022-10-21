package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Language

@Dao
interface LanguageDao {
    @Query("select * from languages")
    fun getAll(): List<Language>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(language: Language)
}