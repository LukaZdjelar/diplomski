package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getAll(): Flow<List<Language>>
    suspend fun insert(language: Language)
    fun getById(id: Long): Language
}