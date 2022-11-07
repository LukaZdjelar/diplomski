package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {
    fun getAll(): Flow<List<Lesson>>
    fun getByChapter(id: Long): Flow<List<Lesson>>
    suspend fun insert(lesson: Lesson)
}