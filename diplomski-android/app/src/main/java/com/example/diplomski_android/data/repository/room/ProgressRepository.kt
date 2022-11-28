package com.example.diplomski_android.data.repository.room

import com.example.diplomski_android.model.Progress

interface ProgressRepository {

    suspend fun insert(progress: Progress): Long
    suspend fun insertAll(progress: List<Progress>)
    fun findByUserAndLessonBoolean(userId: Long, lessonId: Long): Boolean
    suspend fun deleteAll()
}