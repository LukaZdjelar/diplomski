package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {
    fun getAllFlow(): Flow<List<Lesson>>
    fun getAll(): List<Lesson>
    fun getByChapterFlow(id: Long): Flow<List<Lesson>>
    fun getByChapter(id: Long): List<Lesson>
    fun getById(id: Long): Lesson
    suspend fun insert(lesson: Lesson)
}