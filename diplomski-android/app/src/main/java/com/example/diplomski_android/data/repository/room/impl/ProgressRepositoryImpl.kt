package com.example.diplomski_android.data.repository.room.impl

import com.example.diplomski_android.data.dao.ProgressDao
import com.example.diplomski_android.data.repository.room.ProgressRepository
import com.example.diplomski_android.model.Progress

class ProgressRepositoryImpl(private val progressDao: ProgressDao): ProgressRepository {

    override suspend fun insert(progress: Progress): Long {
        return progressDao.insert(progress)
    }

    override suspend fun insertAll(progress: List<Progress>) {
        progressDao.insertAll(progress)
    }

    override fun findByUserAndLessonBoolean(userId: Long, lessonId: Long): Boolean {
        return progressDao.findByUserAndLessonBoolean(userId, lessonId)
    }

    override suspend fun deleteAll() {
        progressDao.deleteAll()
    }
}