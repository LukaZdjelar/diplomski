package com.example.diplomski_android.data.dao

import androidx.room.*
import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    @Query("select * from languages")
    fun getAllFlow(): Flow<List<Language>>

    @Query("select * from languages")
    fun getAll(): List<Language>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: Language): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<Language>)

    @Query("select * from languages where id=:id")
    fun getById(id: Long): Language

    @Delete
    suspend fun delete(language: Language)

    @Query("delete from languages")
    suspend fun deleteAll()
}