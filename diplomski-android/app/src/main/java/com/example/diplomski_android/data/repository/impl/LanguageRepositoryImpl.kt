package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.LanguageDao
import com.example.diplomski_android.data.repository.LanguageRepository
import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(private val languageDao: LanguageDao): LanguageRepository {

    override fun getAllFlow(): Flow<List<Language>> {
        return languageDao.getAllFlow()
    }

    override fun getAll(): List<Language> {
        return languageDao.getAll()
    }

    override suspend fun insert(language: Language){
        return languageDao.insert(language)
    }

    override fun getById(id: Long): Language {
        return languageDao.getById(id)
    }
}