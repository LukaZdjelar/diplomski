package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Chapter

interface ChapterFirestore {
    fun insert(chapter: Chapter)
    suspend fun delete(chapter: Chapter)
    suspend fun deleteByCourse(courseId: Long)
    suspend fun getAll(): List<Chapter>
}