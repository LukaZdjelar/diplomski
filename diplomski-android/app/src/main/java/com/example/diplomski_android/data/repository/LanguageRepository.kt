package com.example.diplomski_android.data.repository

import com.example.diplomski_android.data.dao.LanguageDao
import com.example.diplomski_android.model.Language

class LanguageRepository(private val languageDao: LanguageDao) {

    fun getAll(): List<Language> {
        return languageDao.getAll()
    }
}