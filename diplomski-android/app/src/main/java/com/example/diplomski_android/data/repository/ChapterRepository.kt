package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Chapter

interface ChapterRepository {
    fun getAll(): List<Chapter>
    fun getByCourse(id: Long): List<Chapter>
    suspend fun insert(chapter: Chapter)
}