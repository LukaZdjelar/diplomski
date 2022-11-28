package com.example.diplomski_android.data.repository.room.impl

import com.example.diplomski_android.data.dao.LanguageDao
import com.example.diplomski_android.data.repository.room.LanguageRepository
import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(private val languageDao: LanguageDao): LanguageRepository {

    override fun getAllFlow(): Flow<List<Language>> {
        return languageDao.getAllFlow()
    }

    override fun getAll(): List<Language> {
        return languageDao.getAll()
    }

    override suspend fun insert(language: Language): Long{
        return languageDao.insert(language)
    }

    override suspend fun insertAll(languages: List<Language>) {
        languageDao.insertAll(languages)
    }

    override fun getById(id: Long): Language {
        return languageDao.getById(id)
    }

    override suspend fun deleteAll() {
        languageDao.deleteAll()
    }
}