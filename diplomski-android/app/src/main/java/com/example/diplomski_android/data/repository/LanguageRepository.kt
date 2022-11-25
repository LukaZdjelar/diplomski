package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getAllFlow(): Flow<List<Language>>
    fun getAll(): List<Language>
    suspend fun insert(language: Language)
    suspend fun insertAll(languages: List<Language>)
    fun getById(id: Long): Language
    suspend fun deleteAll()
}