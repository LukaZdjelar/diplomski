package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Language

interface LanguageFirestore {
    fun insert(language: Language)
}