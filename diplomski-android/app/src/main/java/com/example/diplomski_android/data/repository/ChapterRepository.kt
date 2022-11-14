package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.flow.Flow

interface ChapterRepository {
    fun getAllFlow(): Flow<List<Chapter>>
    fun getAll(): List<Chapter>
    fun getByCourseFlow(id: Long): Flow<List<Chapter>>
    fun getByCourse(id: Long): List<Chapter>
    fun getById(id: Long): Chapter
    suspend fun insert(chapter: Chapter)
    suspend fun delete(chapter: Chapter)
    suspend fun deleteByCourse(courseId: Long)
    fun countTotalLessons(chapterId: Long): Int
    fun countCompletedLessons(chapterId: Long, userId: Long): Int
}