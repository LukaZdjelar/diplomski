package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Language

interface LanguageRepository {
    fun getAll(): List<Language>
    suspend fun insert(language: Language)
    fun getById(id: Long): Language
}