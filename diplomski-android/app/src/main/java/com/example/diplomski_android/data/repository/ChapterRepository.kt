package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Chapter

interface ChapterRepository {
    fun getAll(): List<Chapter>
}